package com.smt.smallfat.service.pay.impl;

import com.csyy.common.JSONUtils;
import com.csyy.common.StringDefaultValue;
import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.exception.BusinessException;
import com.csyy.core.exception.CommonException;
import com.csyy.redis.exception.DistributedLockException;
import com.csyy.redis.utils.lock.DistributedLock;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.po.FatCustomer;
import com.smt.smallfat.po.FatNotification;
import com.smt.smallfat.po.FatOrder;
import com.smt.smallfat.po.FatOrderItem;
import com.smt.smallfat.service.base.CustomerService;
import com.smt.smallfat.service.base.GoodsService;
import com.smt.smallfat.service.base.NotificationService;
import com.smt.smallfat.service.base.OrderService;
import com.smt.smallfat.service.pay.*;
import com.smt.smallfat.service.pay.obj.PayCallBackResponse;
import com.smt.smallfat.service.pay.obj.PayResponse;
import com.smt.smallfat.utils.OrderNoCreator;
import com.smt.smallfat.utils.push.IPush;
import com.smt.smallfat.utils.push.PushMessage;
import com.smt.smallfat.utils.push.PushPayloadBuilder;
import com.smt.smallfat.utils.push.pushenum.PlatForm;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.math.BigDecimal;
import java.security.KeyStore;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class PayServiceImpl extends BaseServiceImpl implements PayService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private DistributedLock lock;
    @Autowired
    private IPush push;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private NotificationService notificationService;

    @Override
    public String executePayCallBack(PayCallBackResponse response) {
        String orderNo = response.getOut_trade_no();
        RequestXMLCreator creator = RequestXMLCreator.getInstance();
        FatOrder order = orderService.getOrderByOrderNo(orderNo);

        try {
            //添加订单锁
            // orderService.addOrderLock(order.getId(), response.getTransaction_id());
            lock.lock(Constant.Lock.ORDER_LOCK + order.getId() + "_" + response.getTransaction_id());
            //检查数据状态
            if (order.getState() != Constant.OrderStatus.ORDER_FOR_PAY)
                return creator.buildCallbackErrorResponse("订单已经更新");
            if (order.getTotalPrice().multiply(new BigDecimal(100)).intValue() != StringDefaultValue.intValue(response
                    .getTotal_fee()))
                return creator.buildCallbackErrorResponse("订单金额不匹配");
            order.setTransactionId(response.getTransaction_id());
            order.setState(Constant.OrderStatus.ALREADY_PAY);
            factory.getCacheWriteDataSession().update(FatOrder.class, order);
            orderService.addOrderHistory(order);
            lock.unLock(Constant.Lock.ORDER_LOCK + order.getId() + "_" + response.getTransaction_id());
        } catch (DistributedLockException e) {
            return creator.buildCallbackErrorResponse("订单已锁定");
        }
        return PayConstant.PAY_SUCCESS;
    }

    @Override
    public PayResponse payOrder(String orderNo, String spbillCreateIp) {
        //数据准备
        FatOrder order = orderService.getOrderByOrderNo(orderNo);
        if (order.getState() != Constant.OrderStatus.ORDER_FOR_PAY)
            throw new CommonException(ResultConstant.Order.ORDER_STATUS_INVALID);
        if (order.getState() == Constant.OrderStatus.ALREADY_PAY)
            throw new CommonException(ResultConstant.Order.ORDER_ALREADY_PAY);
        //请求xml构建
        PayRequest request = new PayRequest();
        request.setSpbill_create_ip(spbillCreateIp);
        request.setNonce_str(order.getUuid());
        request.setBody("花兮-植物购买");
        request.setOut_trade_no(order.getOrderNo());
        request.setTotal_fee(order.getTotalPrice().multiply(new BigDecimal(100)).intValue());
        request.setSpbill_create_ip(spbillCreateIp);
        RequestXMLCreator xmlCreator = RequestXMLCreator.getInstance();
        String requestXml = xmlCreator.buildXmlString(xmlCreator.getPayRequestMap(request), PayConstant.WECHAT_OPENID_PAY_APPSECRET);
        logger.debug("请求下单的xml为：{}", requestXml);
        try {
            return sendPaymentToWeChatServer(requestXml, order.getUuid());
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
    }

    private PayResponse sendPaymentToWeChatServer(String xml, String uuid) throws IOException {
        RequestXMLCreator xmlCreator = RequestXMLCreator.getInstance();
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(PayConstant.WECHAT_PAY_URL);
        StringEntity myEntity = new StringEntity(xml, "UTF-8");
        httppost.addHeader("Content-Type", "text/xml");
        httppost.setEntity(myEntity);
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();
        InputStreamReader reader = new InputStreamReader(
                resEntity.getContent(), "UTF-8");
        StringBuffer resultStr = new StringBuffer();
        char[] buff = new char[1024];
        int length = 0;
        while ((length = reader.read(buff)) != -1) {
            resultStr.append(new String(buff, 0, length));
        }
        logger.info("请求返回的xml为：{}", resultStr.toString());
        Map<String, String> responseResult = xmlCreator.getXmlMapResult(resultStr.toString());
        if (!PayConstant.PAY_SUCCESS.equals(responseResult.get("return_code"))
                || !PayConstant.PAY_SUCCESS.equals(responseResult
                .get("result_code")))
            throw new CommonException(ResultConstant.Order.PRE_PAY_FAILED);

        PayResponse payResponse = xmlCreator.getPayResponseMap(responseResult, uuid);
        logger.info("给前端返回的对象数据为：{}", JSONUtils.toJson(payResponse));
        return payResponse;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refund(String orderNo) {
        FatOrder order = orderService.getOrderByOrderNo(orderNo);
        String lockStr = order.getTransactionId() + Constant.Separator.UPDERLINE + "refund";
        try {
            //  orderService.addOrderLock(order.getId(), lockStr);
            lock.lock(Constant.Lock.REFUND_LOCK + lockStr);
            order = orderService.getOrderByOrderNo(orderNo);
            if (order.getState() != Constant.OrderStatus.PAY_FOR_BACK)
                throw new CommonException(ResultConstant.Order.ORDER_STATUS_INVALID);
            //构建请求xml
            Map<String, String> parameters = buildRefundMap(order);
            String requestXml = RequestXMLCreator.getInstance().buildXmlString(parameters, PayConstant
                    .WECHAT_OPENID_PAY_APPSECRET);
            logger.debug("请求的xml为：{}", requestXml);
            try {
                String response_xml = doRefund(PayConstant.WECHAT_PAY_BACK_URL, requestXml);
                Map<String, String> resultMap = RequestXMLCreator.getInstance().getXmlMapResult(response_xml);
                String return_code = StringDefaultValue.StringValue(resultMap.get("return_code"));
                String result_code = StringDefaultValue.StringValue(resultMap.get("result_code"));
                String err_code_des = StringDefaultValue.StringValue(resultMap.get("err_code_des"));
                if (return_code.equalsIgnoreCase(PayConstant.PAY_SUCCESS) && result_code.equalsIgnoreCase(PayConstant
                        .PAY_SUCCESS)) {
                    order.setState(Constant.OrderStatus.ALREADY_PAY_FOR_BACK);
                    order.setRefundNo(StringDefaultValue.StringValue(resultMap.get("out_refund_no")));
                    factory.getCacheWriteDataSession().update(FatOrder.class, order);
                    orderService.addOrderHistory(order);
                } else {
                    throw new CommonException(ResultConstant.Order.REFUND_FAILED, err_code_des);
                }
                logger.info("得到微信的返回结果是:{}", response_xml);
            } catch (Exception e) {
                logger.info("获取微信的结果xml是:");
                throw new BusinessException(e);
            }
            /////////////推送
            FatCustomer customer = customerService.getCustomerById(order.getCustomerId());
            FatNotification notification = FatNotification.getInstance(FatNotification.class);
            String content = "亲爱的，{0}，您订单编号为：{1}的货物已帮您退款。";
            notification.setContent(PushMessage.buildMessage(content, customer.getNickName(), order.getOrderNo()));
            notification.setNickName(customer.getNickName());
            notification.setTitle("退款成功");
            notification.setUserId(customer.getId());
            notification = notificationService.addNotification(notification);
            PushMessage message = PushMessage.get().platform(PlatForm.ALL).addAlias(customer.getUuid()).addExtras
                    (FatNotification.FIELD_ID, StringDefaultValue.StringValue(notification.getId())).addExtras
                    (Constant.PUSH_TYPE, Constant.PushType.SYSTEM_NOTICE).title(notification.getContent()).content
                    (notification.getContent());
            push.push(PushPayloadBuilder.newInstance().build(message));
            //退库存
            List<FatOrderItem> orderItems = orderService.getOrderItemListByOrderId(order.getId());
            for (FatOrderItem item : orderItems) {
                goodsService.returnInventory(item.getGoodsDetailId(), item.getTotalCount());
            }

            ////////////
//            orderService.cancelOrderLock(order.getId(), lockStr);
            lock.unLock(Constant.Lock.REFUND_LOCK + lockStr);
        } catch (DistributedLockException e) {
            throw new CommonException(ResultConstant.Order.ORDER_IS_LOCKED);
        }
    }

    private Map<String, String> buildRefundMap(FatOrder order) {
        Map<String, String> parameters = new TreeMap<>();
        parameters.put("appid", PayConstant.WECHAT_OPENID_APPID);
        parameters.put("mch_id", PayConstant.WECHAT_OPENID_FRONTNO);
        parameters.put("nonce_str", order.getUuid());
        parameters.put("out_trade_no", order.getOrderNo());
        parameters.put("transaction_id", order.getTransactionId());
        String out_refund_no = OrderNoCreator.createOrderNo(Constant.REFUND_BUFF);
        parameters.put("out_refund_no", out_refund_no);
        BigDecimal totalFee = order.getTotalPrice().multiply(new BigDecimal(100));
        String totalFeeStr = totalFee.toString();
        totalFeeStr = totalFeeStr.substring(0, totalFeeStr.indexOf(Constant.Separator.DOT));
        parameters.put("total_fee", totalFeeStr);
        parameters.put("refund_fee", totalFeeStr);
        return parameters;
    }

    /**
     * 退款支付的調用
     *
     * @param url
     * @param data
     * @return
     * @throws Exception
     */
    public static String doRefund(String url, String data) throws Exception {

        /**
         * 注意PKCS12证书 是从微信商户平台-》账户设置-》 API安全 中下载的
         */
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        String keyPath = PayConstant.WECHAT_REFUND_KEY_PATH;
        FileInputStream instream = new FileInputStream(new File(keyPath));//P12文件目录
        try {
            /**
             * 此处要改
             * */
            keyStore.load(instream, PayConstant.WECHAT_OPENID_FRONTNO.toCharArray());//这里写密码..默认是你的MCHID
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        /**
         * 此处要改
         * */
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, PayConstant.WECHAT_OPENID_FRONTNO.toCharArray())//这里也是写密码的
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        try {
            HttpPost httpost = new HttpPost(url); // 设置响应头信息
            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            httpost.setEntity(new StringEntity(data, "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httpost);
            try {
                HttpEntity entity = response.getEntity();
                String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                EntityUtils.consume(entity);
                return jsonStr;
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
}
