package com.smt.smallfat.service.pay;


public interface PayService {
    String executePayCallBack(PayCallBackResponse response);

    PayResponse payOrder(String orderNo, String spbillCreateIp);

    RefundResponse refund(String orderNo);
}
