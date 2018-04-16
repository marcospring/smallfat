package com.smt.smallfat.web.backend.circle;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.obj.Pagination;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.po.FatSucculentCircle;
import com.smt.smallfat.po.FatSucculentReport;
import com.smt.smallfat.service.house.CircleService;
import com.smt.smallfat.vo.house.FlowerHouseItemVO;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/backend/circle")
public class SucculentCircleController extends BaseController{

    @Autowired
    private CircleService circleService;

    @RequestMapping("/pageReport")
    public void pageReport(HttpServletRequest request, HttpServletResponse  response){
        Map<String,Object> params = getRequestParams(request);
        Pagination<FatSucculentReport> page = circleService.pageReport(params);
        printWriter(response,successResultJSON(page));
    }

    @RequestMapping("/viewReport")
    public void viewReport(HttpServletRequest request, HttpServletResponse  response){
        Map<String,Object> params = nullAbleValidation(request,FatSucculentReport.FIELD_ID);
        int id = StringDefaultValue.intValue(params.get(FatSucculentReport.FIELD_ID));
        FatSucculentReport report = circleService.getReportById(id);
        printWriter(response,successResultJSON(report));
    }


    @RequestMapping(value = "/circleView", method = RequestMethod.POST)
    public void circleView(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = nullAbleValidation(request, FatSucculentCircle.FIELD_ID);
        int id = StringDefaultValue.intValue(params.get(FatSucculentCircle.FIELD_ID));
        FlowerHouseItemVO vo = circleService.circleItemView(0, id);
        printWriter(response, successResultJSON(vo));
    }

    @RequestMapping("/pass")
    public void pass(HttpServletRequest request, HttpServletResponse  response){
        Map<String,Object> param = nullAbleValidation(request,FatSucculentReport.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatSucculentReport.FIELD_ID));
        String feedBack = StringDefaultValue.StringValue(param.get(FatSucculentReport.FIELD_REPORT_FEEDBACK));
        circleService.pass(id,feedBack);
        printWriter(response,successResultJSON());
    }

    @RequestMapping("/unPass")
    public void unPass(HttpServletRequest request, HttpServletResponse  response){
        Map<String,Object> param = nullAbleValidation(request,FatSucculentReport.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatSucculentReport.FIELD_ID));
        circleService.unPass(id);
        printWriter(response,successResultJSON());
    }
}
