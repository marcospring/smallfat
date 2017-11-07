package com.smt.smallfat.web.app;

import com.csyy.common.MD5;
import com.csyy.common.StringDefaultValue;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.po.FatOrder;
import com.smt.smallfat.service.pay.*;
import com.smt.smallfat.web.common.BaseController;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/app/pay")
public class PayCallBackController extends BaseController {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private PayService payService;

    @RequestMapping("/callback")
    public void payCallBack(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        String params = null;
        try {
            out = response.getWriter();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    request.getInputStream()));
            StringBuffer result = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            params = result.toString();
            logger.info("wechat request params:" + params);
            PayCallBackResponse callBackResponse = RequestXMLCreator.getInstance().buildResponse(params);
            //如果返回结果为失败则直接返回
            if (!PayConstant.PAY_SUCCESS.equalsIgnoreCase(callBackResponse.getReturn_code()) ||
                    !PayConstant.PAY_SUCCESS.equalsIgnoreCase(callBackResponse.getResult_code())) {
                out.print(RequestXMLCreator.getInstance().buildCallbackErrorResponse("支付失败"));
            }

            String sign = callBackResponse.getSign();
            String mySign = RequestXMLCreator.getInstance().createCallbackSign(callBackResponse.getDoc());
            logger.info("回调签名：{}",sign);
            logger.info("计算签名：{}",mySign);
            if (!mySign.equals(sign)) {
                out.print(RequestXMLCreator.getInstance().buildCallbackErrorResponse("签名验证失败"));
            }

            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>回调成功啦！！！！！！<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            String resultResponse = payService.executePayCallBack(callBackResponse);
            out.print(resultResponse);
        } catch (Exception e) {
            out.print(params);
            logger.error("wechat pay callback error", e);
            e.printStackTrace();
            return;
        } finally {
            out.flush();
            out.close();
        }

    }

    @RequestMapping("/create")
    public void createOrder(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, Constant.PayParam.SPBILL_CREATE_IP, FatOrder
                .FIELD_ORDER_NO);
        String orderNo = StringDefaultValue.StringValue(param.get(FatOrder.FIELD_ORDER_NO));
        String spbillCreateIp = StringDefaultValue.StringValue(param.get(Constant.PayParam.SPBILL_CREATE_IP));
        PayResponse payResponse = payService.payOrder(orderNo, spbillCreateIp);
        printWriter(response, successResultJSON(payResponse));
    }
}
