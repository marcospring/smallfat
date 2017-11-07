package com.smt.smallfat.web.backend.customer;


import com.csyy.common.StringDefaultValue;
import com.csyy.core.obj.Pagination;
import com.smt.smallfat.po.FatSuggest;
import com.smt.smallfat.service.SuggestService;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/suggest")
public class SuggestController extends BaseController {
    @Autowired
    private SuggestService suggestService;

    @RequestMapping("/list")
    public void list(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = getRequestParams(request);
        Pagination<FatSuggest> page = suggestService.paginationSuggest(param);
        printWriter(response, successResultJSON(page));
    }

    @RequestMapping("/getSuggestById")
    public void getSuggestById(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatSuggest.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatSuggest.FIELD_ID));
        FatSuggest suggest = suggestService.getSuggestById(id);
        printWriter(response, successResultJSON(suggest));
    }

    @RequestMapping("/delete")
    public void delete(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatSuggest.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatSuggest.FIELD_ID));
        suggestService.deleteSuggest(id);
        printWriter(response, successResultJSON());
    }
}
