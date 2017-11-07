package com.smt.smallfat.exception;

import com.csyy.core.exception.BusinessException;
import com.smt.smallfat.constant.ResultConstant;

public class GetGoodsDetailLockException extends BusinessException{
    private int goodsDetailId;
    public GetGoodsDetailLockException(int goodsDetailId) {
        super.exceptionKey = ResultConstant.Goods.GOODS_DETAIL_IS_LOCKED;
        this.goodsDetailId = goodsDetailId = goodsDetailId;
    }

    public int getGoodsDetailId() {
        return goodsDetailId;
    }

}
