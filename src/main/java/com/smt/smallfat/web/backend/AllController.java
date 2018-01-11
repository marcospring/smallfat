package com.smt.smallfat.web.backend;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.obj.Pagination;
import com.smt.smallfat.po.FatAll;
import com.smt.smallfat.service.base.AllService;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/backend/all")
public class AllController extends BaseController{

    @Autowired
    private AllService allService;

    @RequestMapping("/list")
    public void list(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = getRequestParams(request);
        Pagination<FatAll> all = allService.pageAll(param);
        printWriter(response,successResultJSON(all));
    }

    @RequestMapping("/addAll")
    public void addAll(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = nullAbleValidation(request,FatAll.FIELD_LITTLE_TITLE,FatAll.FIELD_SUBJECT,FatAll
                .FIELD_CATEGORY);
        FatAll all = allService.addAll(param);
        printWriter(response,successResultJSON(all));
    }

    @RequestMapping("/deleteAll")
    public void deleteAll(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = nullAbleValidation(request,FatAll.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatAll.FIELD_ID));
        allService.deleteAll(id);
        printWriter(response,successResultJSON());
    }

    @RequestMapping("/getAllById")
    public void getAllById(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = nullAbleValidation(request,FatAll.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatAll.FIELD_ID));
        FatAll all = allService.getAllById(id);
        printWriter(response,successResultJSON(all));
    }

    @RequestMapping("/updateAll")
    public void updateAll(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = nullAbleValidation(request,FatAll.FIELD_ID,FatAll.FIELD_LITTLE_TITLE,FatAll
                        .FIELD_SUBJECT,
                FatAll
                .FIELD_CATEGORY);
        FatAll all = allService.updateAll(param);
        printWriter(response,successResultJSON(all));
    }

}
