package com.smt.smallfat.service.pay;


import java.io.IOException;
import java.util.Properties;

public class PayConstant {

    private static Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("properties/pay.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String REQUEST_METHOD_POST = "POST";  //请求方式.
    public static final String REQUEST_METHOD_GET = "GET";  //请求方式.
    /**
     * 编码方式.
     */
    public static final String ENCODING = "UTF-8";  //编码方式.

    public static String WECHAT_OPENID_APPID = properties.getProperty("pay.app_id");
    public static String WECHAT_OPENID_FRONTNO = properties.getProperty("pay.front_no");
    public static String WECHAT_OPENID_NOTIFY_URL = properties.getProperty("pay.notify_url");
    public static String WECHAT_OPENID_TRADE_TYPE = properties.getProperty("pay.trade_type");
    public static String WECHAT_OPENID_PAY_APPSECRET = properties.getProperty("pay.app_secret");
    public static String WECHAT_PAY_URL = properties.getProperty("pay.pay_url");
    public static String WECHAT_PAY_BACK_URL =properties.getProperty("pay.pay_back_url");
    public static String WECHAT_REFUND_KEY_PATH=properties.getProperty("pay.wechat_refund_key_path");
    public static final String PACKAGE = "Sign=WXPay";
    public static final String PAY_SUCCESS = "SUCCESS";
    public static final String OK = "OK";

    public static String PAY_CALL_BACK_SUCCESS_RESPONSE = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    public static String PAY_CALL_BACK_FAIL_RESPONSE_FRONT =
            "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[";
    public static String PAY_CALL_BACK_FAIL_RESPONSE_END = "]]></return_msg></xml>";


}
