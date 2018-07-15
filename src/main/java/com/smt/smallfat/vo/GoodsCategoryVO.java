package com.smt.smallfat.vo;

import com.smt.smallfat.po.FatGoodsCategory;
import com.smt.smallfat.po.FatGoodsTheme;

import java.util.List;

public class GoodsCategoryVO {
    List<FatGoodsCategory> categories;
    List<FatGoodsTheme> themes;

    public GoodsCategoryVO(List<FatGoodsCategory> categories, List<FatGoodsTheme> themes) {
        this.categories = categories;
        this.themes = themes;
    }

    public GoodsCategoryVO() {
    }

    public List<FatGoodsCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<FatGoodsCategory> categories) {
        this.categories = categories;
    }

    public List<FatGoodsTheme> getThemes() {
        return themes;
    }

    public void setThemes(List<FatGoodsTheme> themes) {
        this.themes = themes;
    }
}
