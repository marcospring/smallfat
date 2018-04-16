package com.smt.smallfat.web.app;

import com.smt.smallfat.service.system.SysDicItemService;
import com.smt.smallfat.vo.SysDicItemVo;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/app/common")
public class AppCommonController extends BaseController {

    @Autowired
    private SysDicItemService itemService;
    private static final String ARTICLE_TYPE_CODE = "ARTICLE_TYPE";
    private static final String REPORT_TYPE_CODE = "REPORT_TYPE";

    @RequestMapping("/articleType")
    public void articleType(HttpServletResponse response, HttpServletRequest request) {
        List<SysDicItemVo> list = itemService.getDicItemByDicCode(ARTICLE_TYPE_CODE);
        printWriter(response, successResultJSON(list));
    }

    @RequestMapping("/reportType")
    public void reportType(HttpServletResponse response, HttpServletRequest request) {
        List<SysDicItemVo> list = itemService.getDicItemByDicCode(REPORT_TYPE_CODE);
        printWriter(response, successResultJSON(list));
    }
}
