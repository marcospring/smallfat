package com.smt.smallfat.web.backend.goods;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.obj.Pagination;
import com.smt.smallfat.po.FatGoodsTheme;
import com.smt.smallfat.service.base.GoodsService;
import com.smt.smallfat.service.base.ThemeService;
import com.smt.smallfat.vo.GoodsVO;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/backend/theme")
public class ThemeController extends BaseController {

    @Autowired
    private ThemeService themeService;
    @Autowired
    private GoodsService goodsService;
    @RequestMapping("/addTheme")
    public void addTheme(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = nullAbleValidation(request, FatGoodsTheme.FIELD_THEME_TITLE,FatGoodsTheme
                .FIELD_THEME_IMAGE);
        FatGoodsTheme theme = themeService.addTheme(param);
        printWriter(response,successResultJSON(theme));
    }
    @RequestMapping("/delTheme")
    public void delTheme(HttpServletRequest request, HttpServletResponse response){
        int id = StringDefaultValue.intValue(nullAbleValidation(request,FatGoodsTheme.FIELD_ID).get(FatGoodsTheme
                .FIELD_ID));
        themeService.delTheme(id);
        printWriter(response,successResultJSON());
    }
    @RequestMapping("/updateTheme")
    public void updateTheme(HttpServletRequest request, HttpServletResponse response){
      Map<String,Object> param = nullAbleValidation(request,FatGoodsTheme.FIELD_ID);
        FatGoodsTheme theme = themeService.updateTheme(param);
        printWriter(response,successResultJSON(theme));
    }
    @RequestMapping("/pageTheme")
    public void pageTheme(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = getRequestParams(request);
        Pagination<FatGoodsTheme> page = themeService.allTheme(param);
        printWriter(response,successResultJSON(page));
    }
    @RequestMapping("/viewTheme")
    public void viewTheme(HttpServletRequest request, HttpServletResponse response){
        int id = StringDefaultValue.intValue(nullAbleValidation(request,FatGoodsTheme.FIELD_ID).get(FatGoodsTheme
                .FIELD_ID));
        FatGoodsTheme theme = themeService.getThemeById(id);
        printWriter(response,successResultJSON(theme));
    }
    @RequestMapping("/publishTheme")
    public void publishTheme(HttpServletRequest request, HttpServletResponse response){
        int id = StringDefaultValue.intValue(nullAbleValidation(request,FatGoodsTheme.FIELD_ID).get(FatGoodsTheme
                .FIELD_ID));
        themeService.publishTheme(id);
        printWriter(response,successResultJSON());
    }
    @RequestMapping("/cancelTheme")
    public void cancelTheme(HttpServletRequest request, HttpServletResponse response){
        int id = StringDefaultValue.intValue(nullAbleValidation(request,FatGoodsTheme.FIELD_ID).get(FatGoodsTheme
                .FIELD_ID));
        themeService.cancelTheme(id);
        printWriter(response,successResultJSON());
    }
    @RequestMapping("/themeGoodsList")
    public void themeGoodsList(HttpServletRequest request, HttpServletResponse response){
        int id = StringDefaultValue.intValue(nullAbleValidation(request,FatGoodsTheme.FIELD_ID).get(FatGoodsTheme
                .FIELD_ID));
        List<GoodsVO> vo = goodsService.goodsThemeList(id);
        printWriter(response,successResultJSON(vo));

    }

    @RequestMapping("/allThemeList")
    public void allThemeList(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> params = getRequestParams(request);
        List<FatGoodsTheme> list = themeService.themeList(params);
        printWriter(response,successResultJSON(list));

    }
}
