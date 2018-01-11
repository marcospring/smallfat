package com.smt.smallfat.web.common;

import com.csyy.common.StringDefaultValue;
import com.smt.smallfat.po.FatArea;
import com.smt.smallfat.service.system.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/common")
public class AreaController extends BaseController {

    @Autowired
    private AreaService areaService;




    @RequestMapping("/areaListByParentId")
    public void areaListByParentId(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatArea.FIELD_PARENT_ID);
        int parentId = StringDefaultValue.intValue(param.get(FatArea.FIELD_PARENT_ID));
        List<FatArea> result = areaService.getAreaListByParentId(parentId);
        printWriter(response, successResultJSON(result));
    }

    @RequestMapping("/areaListByFirstLetter")
    public void areaListByFirstLetter(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatArea.FIELD_FIRST_LETTER);
        String letter = StringDefaultValue.StringValue(param.get(FatArea.FIELD_FIRST_LETTER));
        List<FatArea> result = areaService.getAreaListByFirstLetter(letter);
        printWriter(response, successResultJSON(result));
    }

}
