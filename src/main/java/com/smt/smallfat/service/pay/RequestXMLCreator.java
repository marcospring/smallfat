package com.smt.smallfat.service.pay;

import com.csyy.common.MD5;
import com.csyy.common.StringDefaultValue;
import com.smt.smallfat.service.pay.obj.PayCallBackResponse;
import com.smt.smallfat.service.pay.obj.PayResponse;
import com.smt.smallfat.utils.XmlUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class RequestXMLCreator {
    private XmlUtils requestMoudle;
    private static RequestXMLCreator instance;
    Logger logger = LoggerFactory.getLogger(getClass());

    private RequestXMLCreator() {
    }

    public static RequestXMLCreator getInstance() {
        if (instance == null)
            instance = new RequestXMLCreator();
        return instance;
    }

    public String buildXmlString(Map<String, String> value, String appkey) {
        requestMoudle = new XmlUtils();
        for (String key : value.keySet()) {
            requestMoudle.addRootNode(key, value.get(key));
        }
        String sign = createSign(value, appkey);
        requestMoudle.addRootNode("sign", sign);
        return requestMoudle.getDoc().getRootElement().asXML();
    }


    private String createSign(Map<String, String> value, String appkey) {
        Map<String, String> treeMap = new TreeMap<String, String>(value);
        StringBuffer content = new StringBuffer();
        for (String str : treeMap.keySet()) {
            content.append(str).append("=").append(value.get(str)).append("&");
        }
        content.append("key=").append(appkey);
        logger.info("======>:{}", content.toString());
        String mySign = MD5.md5(content.toString()).toUpperCase();
        return mySign;
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getXmlMapResult(String xml) {
        Map<String, String> result = new HashMap<String, String>();
        requestMoudle = new XmlUtils(xml);
        Element root = requestMoudle.getRoot();
        for (Iterator<Element> iterator = root.elementIterator(); iterator
                .hasNext(); ) {
            Element e = iterator.next();
            result.put(e.getName(), e.getTextTrim());
        }
        return result;
    }

    public Map<String, String> getPayRequestMap(PayRequest request) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("appid", PayConstant.WECHAT_OPENID_APPID);
        paramsMap.put("mch_id", PayConstant.WECHAT_OPENID_FRONTNO);// 商户号
        paramsMap.put("nonce_str", request.getNonce_str());// 随机字符串，选为订单的guid
        paramsMap.put("body", request.getBody());
        paramsMap.put("out_trade_no", request.getOut_trade_no());//
        paramsMap.put("total_fee", StringDefaultValue.StringValue(request.getTotal_fee()));
        paramsMap.put("spbill_create_ip", request.getSpbill_create_ip());
        paramsMap.put("notify_url", PayConstant.WECHAT_OPENID_NOTIFY_URL);
        paramsMap.put("trade_type", PayConstant.WECHAT_OPENID_TRADE_TYPE);// JSAPI代表公众号支付
//		paramsMap.put("time_start", DateUtils.formatDate(new Date(),"yyyyMMddHHmmss"));
//		paramsMap.put("time_expire", DateUtils.formatDate(DateUtils.plusMinute(new Date(),30),"yyyyMMddHHmmss"));
        return paramsMap;
    }

    public PayResponse getPayResponseMap(Map<String, String> param, String uuid) {
        PayResponse response = new PayResponse();
        response.setAppid(param.get("appid"));
        response.setPartnerid(param.get("mch_id"));
        response.setPrepayid(param.get("prepay_id"));
        response.setPackageValue(PayConstant.PACKAGE);
        response.setNoncestr(uuid);
        response.setTimestamp(String.valueOf(System.currentTimeMillis() / 1000));

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("appid", response.getAppid());
        parameters.put("noncestr", response.getNoncestr());
        parameters.put("package", response.getPackageValue());
        parameters.put("partnerid", response.getPartnerid());
        parameters.put("prepayid", response.getPrepayid());
        parameters.put("timestamp", response.getTimestamp());
        String paySign = createSign(parameters,
                PayConstant.WECHAT_OPENID_PAY_APPSECRET);
        response.setSign(paySign);

        return response;
    }

    public PayCallBackResponse buildResponse(String xml) throws DocumentException {
        PayCallBackResponse response = new PayCallBackResponse();
        Document doc = DocumentHelper.parseText(xml);
        Element root = doc.getRootElement();
        response.setReturn_code(StringDefaultValue.StringValue(root.elementText("return_code")));
        response.setAppid(StringDefaultValue.StringValue(root.elementText("appid")));
        response.setBank_type(StringDefaultValue.StringValue(root.elementText("bank_type")));
        response.setCash_fee(StringDefaultValue.StringValue(root.elementText("cash_fee")));
        response.setErr_code(StringDefaultValue.StringValue(root.elementText("err_code")));
        response.setErr_code_des(StringDefaultValue.StringValue(root.elementText("err_code_des")));
        response.setMch_id(StringDefaultValue.StringValue(root.elementText("mch_id")));
        response.setNonce_str(StringDefaultValue.StringValue(root.elementText("nonce_str")));
        response.setOpenid(StringDefaultValue.StringValue(root.elementText("openid")));
        response.setOut_trade_no(StringDefaultValue.StringValue(root.elementText("out_trade_no")));
        response.setResult_code(StringDefaultValue.StringValue(root.elementText("result_code")));
        response.setSign(StringDefaultValue.StringValue(root.elementText("sign")));
        response.setTime_end(StringDefaultValue.StringValue(root.elementText("time_end")));
        response.setTotal_fee(StringDefaultValue.StringValue(root.elementText("total_fee")));
        response.setTrade_type(StringDefaultValue.StringValue(root.elementText("trade_type")));
        response.setTransaction_id(StringDefaultValue.StringValue(root.elementText("transaction_id")));
        response.setDoc(doc);
        return response;
    }

    public String createCallbackSign(Document document) {
        Element root = document.getRootElement();
        Iterator<Element> it = root.elementIterator();
        List<String> list = new ArrayList<String>();
        while (it.hasNext()) {
            Element element = it.next();
            String name = element.getName();
            if ("sign".equalsIgnoreCase(name)) {
                continue;
            }
            list.add(name);
            Collections.sort(list);
        }
        StringBuffer content = new StringBuffer();
        for (String str : list) {
            content.append(str).append("=")
                    .append(root.element(str).getText()).append("&");
        }
        return MD5.md5(content.append("key=" + PayConstant.WECHAT_OPENID_PAY_APPSECRET).toString()).toUpperCase();
    }

    public String buildCallbackErrorResponse(String msg) {
        StringBuilder builder = new StringBuilder(PayConstant.PAY_CALL_BACK_FAIL_RESPONSE_FRONT);
        builder.append(msg).append(PayConstant.PAY_CALL_BACK_FAIL_RESPONSE_END);
        return builder.toString();
    }

}

