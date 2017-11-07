package com.smt.smallfat.exception;

import com.csyy.core.exception.BusinessException;
import com.smt.smallfat.constant.ResultConstant;

public class UnLockOrderException extends BusinessException{
    private int orderId;
    public UnLockOrderException(int orderId) {
        this.orderId = orderId;
        exceptionKey = ResultConstant.Order.ORDER_UN_LOCK_FAILED;
    }

    public int getOrderId() {
        return orderId;
    }
}
