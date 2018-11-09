package com.smt.smallfat.web.backend.freight;

import com.csyy.common.StringDefaultValue;
import com.smt.smallfat.po.FatFreightBody;
import com.smt.smallfat.po.FatFreightBodyArea;
import com.smt.smallfat.po.FatFreightHead;
import com.smt.smallfat.service.base.FreightService;
import com.smt.smallfat.vo.FreightTableItemVO;
import com.smt.smallfat.vo.FreightTableVO;
import com.smt.smallfat.web.common.BaseController;
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
@RequestMapping("/backend/freight")
@Api(tags = "后台运费表功能")
public class FreightController extends BaseController {

    @Autowired
    private FreightService service;

    @ApiOperation(value = "添加运费表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "companyCode", required = true, value = "公司编码，来自于字典，快递公司的编码", dataType =
                    "String"),
            @ApiImplicitParam(paramType = "query", name = "shopId",  value = "商户ID，为以后扩展保留，默认为0",
                    dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "expressType",  value = "快递计费类型，1：按质量；2：按个数",
                    dataType = "int"),
    })
    @RequestMapping(value = "/addFreightTable",method = RequestMethod.POST)
    public void addFreightTable(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatFreightHead.FIELD_COMPANY_CODE);
        if (StringDefaultValue.isEmpty(StringDefaultValue.StringValue(param.get(FatFreightHead.FIELD_SHOP_ID))))
            param.put(FatFreightHead.FIELD_SHOP_ID, 0);
        if (StringDefaultValue.isEmpty(StringDefaultValue.StringValue(param.get(FatFreightHead.FIELD_EXPRESS_TYPE))))
            //默认快递类型为按照千克计算
            param.put(FatFreightHead.FIELD_EXPRESS_TYPE, 1);
        FreightTableVO vo = service.addFreightTable(param);
        printWriter(response, successResultJSON(vo));
    }

    @ApiOperation(value = "添加运费表项")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "areaCode", required = true, value = "地区编码，可以传多个，以'，'隔开",
                    dataType =
                    "String"),
            @ApiImplicitParam(paramType = "query", name = "headId", required = true, value = "添加运费表以后，返回的id",
                    dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "firstCount", required = true, value = "首重",
                    dataType = "double"),
            @ApiImplicitParam(paramType = "query", name = "firstPrice", required = true, value = "运费",
                    dataType = "double"),
            @ApiImplicitParam(paramType = "query", name = "otherCount", required = true, value = "续重",
                    dataType = "double"),
            @ApiImplicitParam(paramType = "query", name = "otherPrice", required = true, value = "续重，运费",
                    dataType = "double"),
    })
    @RequestMapping(value = "/addFreightTableItem",method = RequestMethod.POST)
    public void addFreightTableItem(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FreightService.AREA_CODE, FatFreightBody.FIELD_HEAD_ID,
                FatFreightBody.FIELD_FIRST_COUNT, FatFreightBody.FIELD_FIRST_PRICE, FatFreightBody.FIELD_OTHER_COUNT,
                FatFreightBody.FIELD_OTHER_PRICE);
        FreightTableItemVO vo = service.addFreightTableItem(param);
        printWriter(response, successResultJSON(vo));
    }

    @ApiOperation(value = "添加运费表项中的城市")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "areaCode", required = true, value = "地区编码",
                    dataType =
                            "String"),
            @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "添加的运费表项的Id",
                    dataType = "int"),
    })
    @RequestMapping(value = "/addFreightItemArea",method = RequestMethod.POST)
    public void addFreightItemArea(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatFreightBody.FIELD_ID, FreightService.AREA_CODE);
        int bodyId = StringDefaultValue.intValue(param.get(FatFreightBody.FIELD_HEAD_ID));
        String areaCode = StringDefaultValue.StringValue(param.get(FreightService.AREA_CODE));
        FreightTableItemVO vo = service.addFreightItemArea(bodyId, areaCode);
        printWriter(response, successResultJSON(vo));
    }

    @ApiOperation(value = "查看运费表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "运费表的Id",
                    dataType = "int"),
    })
    @RequestMapping(value = "/viewFreightTable",method = RequestMethod.POST)
    public void viewFreightTable(HttpServletRequest request, HttpServletResponse response) {
        int id = StringDefaultValue.intValue(validation(request, new IdValid()));
        FreightTableVO vo = service.viewFreightTable(id);
        printWriter(response, successResultJSON(vo));
    }

    @ApiOperation(value = "运费表列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "shopId", required = true, value = "商户Id,默认传0",
                    dataType = "int"),
    })
    @RequestMapping(value = "/listFreightTable",method = RequestMethod.POST)
    public void listFreightTable(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatFreightHead.FIELD_SHOP_ID);
        int shopId = StringDefaultValue.intValue(param.get(FatFreightHead.FIELD_SHOP_ID));
        List<FreightTableVO> list = service.listFreightTable(shopId);
        printWriter(response, successResultJSON(list));
    }

    @ApiOperation(value = "更新运费表，修改了传，不修改不传")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "运费表的Id",
                    dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "companyCode", value = "公司编码，来自于字典，快递公司的编码", dataType =
                    "String"),
            @ApiImplicitParam(paramType = "query", name = "shopId",  value = "商户ID，为以后扩展保留，默认为0",
                    dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "expressType",  value = "快递计费类型，1：按质量；2：按个数",
                    dataType = "int"),
    })
    @RequestMapping(value = "/updateFreightTable",method = RequestMethod.POST)
    public void updateFreightTable(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = nullAbleValidation(request, FatFreightHead.FIELD_ID);
        int id = StringDefaultValue.intValue(params.get(FatFreightHead.FIELD_ID));
        FreightTableVO vo = service.updateFreightTable(id, params);
        printWriter(response, successResultJSON(vo));
    }

    @ApiOperation(value = "修改运费表项")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "要修改的运费表项的Id",
                    dataType =
                            "String"),
            @ApiImplicitParam(paramType = "query", name = "areaCode", required = true, value = "地区编码，可以传多个，以'，'隔开",
                    dataType =
                            "String"),
            @ApiImplicitParam(paramType = "query", name = "firstCount", required = true, value = "首重",
                    dataType = "double"),
            @ApiImplicitParam(paramType = "query", name = "firstPrice", required = true, value = "运费",
                    dataType = "double"),
            @ApiImplicitParam(paramType = "query", name = "otherCount", required = true, value = "续重",
                    dataType = "double"),
            @ApiImplicitParam(paramType = "query", name = "otherPrice", required = true, value = "续重，运费",
                    dataType = "double"),
    })
    @RequestMapping(value = "/updateFreightItem",method = RequestMethod.POST)
    public void updateFreightItem(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatFreightBody.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatFreightBody.FIELD_ID));
        FreightTableItemVO vo = service.updateFreightItem(id, param);
        printWriter(response, successResultJSON(vo));
    }

    @ApiOperation(value = "删除运费表列表项")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "运费表项Id",
                    dataType = "int"),
    })
    @RequestMapping(value = "/deleteFreightItem",method = RequestMethod.POST)
    public void deleteFreightItem(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatFreightBody.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatFreightBody.FIELD_ID));
        service.deleteFreightItem(id);
        printWriter(response, successResultJSON());
    }

    @ApiOperation(value = "删除运费表列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "运费表Id",
                    dataType = "int"),
    })
    @RequestMapping(value = "/deleteFreightTable",method = RequestMethod.POST)
    public void deleteFreightTable(HttpServletRequest request, HttpServletResponse response) {
        int id = StringDefaultValue.intValue(validation(request, new IdValid()));
        service.deleteFreightTable(id);
        printWriter(response, successResultJSON());
    }

    @ApiOperation(value = "删除运费表列表项里所有的城市")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", required = true, value = "运费表项Id",
                    dataType = "int"),
    })
    @RequestMapping(value = "/deleteFreightItemArea",method = RequestMethod.POST)
    public void deleteFreightItemArea(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatFreightBodyArea.FIELD_BODY_ID);
        int bodyId = StringDefaultValue.intValue(param.get(FatFreightBodyArea.FIELD_BODY_ID));
        service.deleteFreightItemArea(bodyId);
        printWriter(response,successResultJSON());
    }

    @ApiOperation(value = "根据商户ID获取商户支持的快递公司列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "shopId", required = true, value = "商户Id",
                    dataType = "int"),
    })
    @RequestMapping(value = "/getShopFlexList",method = RequestMethod.POST)
    public void getShopFlexList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request,FatFreightHead.FIELD_SHOP_ID);
        int shopId = StringDefaultValue.intValue(param.get(FatFreightHead.FIELD_SHOP_ID));
        List<Map<String, Object>> result = service.getShopFlexList(shopId);
        printWriter(response,successResultJSON(result));
    }

}
