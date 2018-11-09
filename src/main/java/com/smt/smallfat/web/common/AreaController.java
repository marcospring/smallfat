package com.smt.smallfat.web.common;

import com.csyy.common.StringDefaultValue;
import com.smt.smallfat.po.FatArea;
import com.smt.smallfat.po.FatGoods;
import com.smt.smallfat.service.system.AreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/common")
@Api("地区城市接口API")
public class AreaController extends BaseController {

    @Autowired
    private AreaService areaService;


    @ApiOperation(value = "根据父Id查询城市列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "parentId", required = true, value = "父ID，获取省列表请传入0",
                    dataType = "int"),
    })
    @RequestMapping(value = "/areaListByParentId", method = RequestMethod.POST)
    public void areaListByParentId(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatArea.FIELD_PARENT_ID);
        int parentId = StringDefaultValue.intValue(param.get(FatArea.FIELD_PARENT_ID));
        List<FatArea> result = areaService.getAreaListByParentId(parentId);
        printWriter(response, successResultJSON(result));
    }

    @ApiOperation(value = "根据父首字母查询城市列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "parentId", required = true, value = "城市首字母",
                    dataType = "String"),
    })
    @RequestMapping(value = "/areaListByFirstLetter", method = RequestMethod.POST)
    public void areaListByFirstLetter(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatArea.FIELD_FIRST_LETTER);
        String letter = StringDefaultValue.StringValue(param.get(FatArea.FIELD_FIRST_LETTER));
        List<FatArea> result = areaService.getAreaListByFirstLetter(letter);
        printWriter(response, successResultJSON(result));
    }

    @ApiOperation(value = "根据地区code查询城市列表，可输入城市code部分，可查询以其开头的城市列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "areaCode", required = true, value = "城市code",
                    dataType = "String"),
    })

    @RequestMapping(value = "/areaListByAreaCode", method = RequestMethod.POST)
    public void areaListByAreaCode(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatArea.FIELD_AREA_CODE);
        String code = StringDefaultValue.StringValue(param.get(FatArea.FIELD_AREA_CODE));
        List<FatArea> result = areaService.areaListByAreaCode(code);
        printWriter(response, successResultJSON(result));
    }

}
