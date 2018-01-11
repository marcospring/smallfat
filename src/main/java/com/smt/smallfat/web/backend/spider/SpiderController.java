package com.smt.smallfat.web.backend.spider;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.obj.Pagination;
import com.smt.smallfat.po.FatSpider;
import com.smt.smallfat.service.spider.SpiderService;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Controller
@RequestMapping("/backend/spider")
public class SpiderController extends BaseController{

    @Autowired
    private SpiderService spiderService;

    @RequestMapping("/input")
    public void inputFile(HttpServletRequest request, HttpServletResponse response) throws IOException{
        InputStream in = request.getInputStream();
        spiderService.catchSpiderBest(in);
        printWriter(response,successResultJSON());
    }

    @RequestMapping("/page")
    public void page(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = getRequestParams(request);
        Pagination<FatSpider> page = spiderService.find(param);
        printWriter(response,successResultJSON(page));
    }

    @RequestMapping("/delete")
    public void delete(HttpServletRequest request, HttpServletResponse response){
        int id = StringDefaultValue.intValue(nullAbleValidation(request,FatSpider.FIELD_ID).get(FatSpider.FIELD_ID));
        spiderService.deleteById(id);
        printWriter(response,successResultJSON());
    }

    @RequestMapping("/addToArticle")
    public void addToArticle(HttpServletRequest request, HttpServletResponse response){
        int id = StringDefaultValue.intValue(nullAbleValidation(request,FatSpider.FIELD_ID).get(FatSpider.FIELD_ID));
        spiderService.addBest(id);
        printWriter(response,successResultJSON());
    }

    @RequestMapping("/getSpiderById")
    public void getSpiderById(HttpServletRequest request, HttpServletResponse response){
        int id = StringDefaultValue.intValue(nullAbleValidation(request,FatSpider.FIELD_ID).get(FatSpider.FIELD_ID));
        FatSpider spider = spiderService.findById(id);
        printWriter(response,successResultJSON(spider));
    }
}
