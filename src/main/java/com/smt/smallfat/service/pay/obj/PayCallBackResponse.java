package com.smt.smallfat.service.pay.obj;

import org.dom4j.Document;

public class PayCallBackResponse {
//    应用ID
    private String appid	;
//    商户号
    private String mch_id ;
//    随机字符串		是	String(32)	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	随机字符串，不长于32位
    private String nonce_str;
//    签名		是	String(32)	C380BEC2BFD727A4B6845133519F3AD6	签名，详见签名算法
    private String sign;
//    业务结果		是	String(16)	SUCCESS	SUCCESS/FAIL
    private String result_code;
    private String return_code;

    //    错误代码		否	String(32)	SYSTEMERROR	错误返回的信息描述
    private String err_code;
//    错误代码描述		否	String(128)	系统错误	错误返回的信息描述
    private String err_code_des;
//    用户标识		是	String(128)	wxd930ea5d5a258f4f	用户在商户appid下的唯一标识
    private String openid;
//    交易类型	trade_type	是	String(16)	APP	APP
    private String trade_type;
//    付款银行	bank_type	是	String(16)	CMC	银行类型，采用字符串类型的银行标识，银行类型见银行列表
    private String bank_type;
//    总金额		是	Int	100	订单总金额，单位为分
    private String total_fee;
//    现金支付金额	是	Int	100	现金支付金额订单现金支付金额，详见支付金额
    private String cash_fee;
//    微信支付订单号		是	String(32)	1217752501201407033233368018	微信支付订单号
    private String transaction_id;
//    商户订单号		是	String(32)	1212321211201407033568112322	商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
    private String out_trade_no;
//    支付完成时间	time_end	是	String(14)	20141030133525	支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
    private String time_end;

    private String fee_type;
    private String is_subscribe;

    private Document doc;

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getBank_type() {
        return bank_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(String cash_fee) {
        this.cash_fee = cash_fee;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }
}
