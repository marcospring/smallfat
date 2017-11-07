package com.smt.smallfat.web.app;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.obj.Pagination;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.po.FatAll;
import com.smt.smallfat.service.AllService;
import com.smt.smallfat.vo.AllCountVO;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/app/all")
public class AppAllController extends BaseController {

    @Autowired
    private AllService allService;

    @RequestMapping("/list")
    public void list(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = getRequestParams(request);
        Pagination<FatAll> page = allService.pageAll(param);
        printWriter(response, successResultJSON(page));
    }

    @RequestMapping("/allSubjectCount")
    public void allSubjectCount(HttpServletRequest request, HttpServletResponse response) {
        List<AllCountVO> list = allService.getAllSubjectCount();
        printWriter(response, successResultJSON(list));
    }

    @RequestMapping("/getAllById")
    public void getAllById(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatAll.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatAll.FIELD_ID));
        FatAll all = allService.getAllById(id);
        all.setShareUrl(Constant.ALL_SHARE_URL + id);
        printWriter(response, successResultJSON(all));
    }


}
