package com.smt.smallfat.exception;

import com.csyy.core.exception.BusinessException;
import com.smt.smallfat.constant.ResultConstant;

public class UnLockGoodsDetailException extends BusinessException{
    private int goodsDetailId;
    public UnLockGoodsDetailException(int goodsDetailId) {
        exceptionKey = ResultConstant.Goods.GOODS_DETAIL_UN_LOCK_FAILED;
        this.goodsDetailId = goodsDetailId;
    }
}
