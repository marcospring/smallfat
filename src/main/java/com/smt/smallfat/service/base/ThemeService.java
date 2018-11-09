package com.smt.smallfat.service.base;

import com.csyy.core.obj.Pagination;
import com.smt.smallfat.po.FatGoodsTheme;
import com.smt.smallfat.vo.GoodsVO;

import java.util.List;
import java.util.Map;

public interface ThemeService {
    FatGoodsTheme addTheme(Map<String,Object> params);
    void delTheme(int id);
    FatGoodsTheme updateTheme(Map<String,Object> params);
    Pagination<FatGoodsTheme> allTheme(Map<String,Object> params);
    List<FatGoodsTheme> indexTheme();
    void publishTheme(int id);
    void cancelTheme(int id);
    FatGoodsTheme getThemeById(int id);
    List<FatGoodsTheme> themeList(Map<String, Object> params);
}
