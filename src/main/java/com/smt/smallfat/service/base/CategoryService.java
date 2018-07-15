package com.smt.smallfat.service.base;

import com.smt.smallfat.po.FatGoodsCategory;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    FatGoodsCategory addCategory(Map<String,Object> param);
    void delCategory(int id);
    FatGoodsCategory updateCategory(Map<String,Object> param,int id);
    List<FatGoodsCategory> categoryList();
    void publishCategory(int id);
    void cancelCategory(int id);
    FatGoodsCategory getCategoryById(int id);
}
