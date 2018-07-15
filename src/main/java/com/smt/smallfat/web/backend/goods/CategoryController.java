package com.smt.smallfat.web.backend.goods;

import com.csyy.common.StringDefaultValue;
import com.smt.smallfat.po.FatGoodsCategory;
import com.smt.smallfat.service.base.CategoryService;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/backend/category")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/addGoodsCategory")
    public void addGoodsCategory(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatGoodsCategory.FIELD_CATEGORY_NAME);
        FatGoodsCategory category = categoryService.addCategory(param);
        printWriter(response, successResultJSON(category));
    }

    @RequestMapping("/delGoodsCategory")
    public void delGoodsCategory(HttpServletRequest request, HttpServletResponse response) {
        int id = StringDefaultValue.intValue(nullAbleValidation(request, FatGoodsCategory.FIELD_ID).get(FatGoodsCategory
                .FIELD_ID));
        categoryService.delCategory(id);
        printWriter(response, successResultJSON());
    }

    @RequestMapping("/updateGoodsCategory")
    public void updateGoodsCategory(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatGoodsCategory.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatGoodsCategory.FIELD_ID));
        FatGoodsCategory category = categoryService.updateCategory(param, id);
        printWriter(response, successResultJSON(category));
    }

    @RequestMapping("/listGoodsCategory")
    public void listGoodsCategory(HttpServletRequest request, HttpServletResponse response) {
        printWriter(response,successResultJSON(categoryService.categoryList()));
    }

    @RequestMapping("/viewGoodsCategory")
    public void viewGoodsCategory(HttpServletRequest request, HttpServletResponse response) {
        int id = StringDefaultValue.intValue(nullAbleValidation(request, FatGoodsCategory.FIELD_ID).get(FatGoodsCategory
                .FIELD_ID));
        printWriter(response,successResultJSON(categoryService.getCategoryById(id)));
    }
    @RequestMapping("/publishCategory")
    public void publishCategory(HttpServletRequest request, HttpServletResponse response){
        int id = StringDefaultValue.intValue(nullAbleValidation(request, FatGoodsCategory.FIELD_ID).get(FatGoodsCategory
                .FIELD_ID));
        categoryService.publishCategory(id);
        printWriter(response,successResultJSON());
    }
    @RequestMapping("/cancelPublishCategory")
    public void cancelPublishCategory(HttpServletRequest request, HttpServletResponse response){
        int id = StringDefaultValue.intValue(nullAbleValidation(request, FatGoodsCategory.FIELD_ID).get(FatGoodsCategory
                .FIELD_ID));
        categoryService.cancelCategory(id);
        printWriter(response,successResultJSON());
    }

}
