package com.smt.smallfat.exception;

import com.csyy.core.exception.BusinessException;
import com.smt.smallfat.constant.ResultConstant;

public class GetOrderLockException extends BusinessException{
    private int orderId;
    public GetOrderLockException(int orderId) {
        exceptionKey = ResultConstant.Order.ORDER_IS_LOCKED;
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }
}
