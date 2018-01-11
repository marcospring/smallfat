package com.smt.smallfat.service.pay;


import com.smt.smallfat.service.pay.obj.PayCallBackResponse;
import com.smt.smallfat.service.pay.obj.PayResponse;

public interface PayService {
    String executePayCallBack(PayCallBackResponse response);

    PayResponse payOrder(String orderNo, String spbillCreateIp);

    void refund(String orderNo);
}
