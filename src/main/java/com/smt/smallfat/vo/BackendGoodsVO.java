package com.smt.smallfat.vo;

import com.smt.smallfat.po.FatGoods;

import java.util.List;

public class BackendGoodsVO {
    private FatGoods goods;
    private List<Integer> themeIds;

    public FatGoods getGoods() {
        return goods;
    }

    public void setGoods(FatGoods goods) {
        this.goods = goods;
    }

    public List<Integer> getThemeIds() {
        return themeIds;
    }

    public void setThemeIds(List<Integer> themeIds) {
        this.themeIds = themeIds;
    }
}
