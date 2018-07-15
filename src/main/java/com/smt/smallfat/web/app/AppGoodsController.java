package com.smt.smallfat.web.app;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.obj.Pagination;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.po.FatGoods;
import com.smt.smallfat.service.base.GoodsService;
import com.smt.smallfat.vo.GoodsCategoryVO;
import com.smt.smallfat.vo.GoodsVO;
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
@RequestMapping("/app/goods")
@Api(tags = "商品")
public class AppGoodsController extends BaseController {

    @Autowired
    private GoodsService goodsService;


    @ApiOperation(value = "商品列表", notes = "商品列表，可根据商品字段进行查询，根据商家时间倒序")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNo", value = "页码", dataType =
                    "int"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页条数", dataType =
                    "int"),
            @ApiImplicitParam(paramType = "query", name = "categoryId", value = "种类ID", dataType =
                    "int"),
    })
    @RequestMapping(value = "/goodsList", method = RequestMethod.POST)
    public void goodsList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = getRequestParams(request);
        Pagination<GoodsVO> list = goodsService.pageGoodsVO(params);
        printWriter(response, successResultJSON(list));
    }

    @ApiOperation(value = "商品商品详情", notes = "商品商品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "商品ID", dataType =
                    "int"),
    })
    @RequestMapping(value = "/viewGoods", method = RequestMethod.POST)
    public void viewGoods(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatGoods.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatGoods.FIELD_ID));
        GoodsVO vo = goodsService.getGoodsVO(id);
        vo.setShareUrl(Constant.GOODS_SHARE_URL + id);
        printWriter(response, successResultJSON(vo));
    }

    @ApiOperation(value = "商品种类列表", notes = "商品种类列表")
    @RequestMapping(value = "/listGoodsCategory", method = RequestMethod.POST)
    public void listGoodsCategory(HttpServletRequest request, HttpServletResponse response) {
        GoodsCategoryVO vo = goodsService.listGoodsCategory();
        printWriter(response, successResultJSON(vo));
    }

    @ApiOperation(value = "根据主题查询主题下的商品列表", notes = "根据主题查询主题下的商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "themeId", value = "主题ID", dataType =
                    "int"),
    })
    @RequestMapping(value = "/goodsThemeList", method = RequestMethod.POST)
    public void goodsThemeList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = nullAbleValidation(request, "themeId");
        int themeId = StringDefaultValue.intValue(params.get("themeId"));
        List<GoodsVO> list = goodsService.goodsThemeList(themeId);
        printWriter(response, successResultJSON(list));
    }

    @ApiOperation(value = "获取最近一天发布的商品的列表", notes = "获取最近一天发布的商品的列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "themeId", value = "主题ID", dataType =
                    "int"),
    })
    @RequestMapping(value = "/nearestGoodsList", method = RequestMethod.POST)
    public void nearestGoodsList(HttpServletRequest request, HttpServletResponse response) {
        List<GoodsVO> list = goodsService.nearestGoodsList();
        printWriter(response, successResultJSON(list));
    }
    @ApiOperation(value = "非今日推荐商品列表", notes = "商品列表，可根据商品字段进行查询，根据发布时间倒序")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNo", value = "页码", dataType =
                    "int"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页条数", dataType =
                    "int"),
            @ApiImplicitParam(paramType = "query", name = "categoryId", value = "种类ID", dataType =
                    "int"),
    })
    @RequestMapping(value = "/otherGoodsList",method = RequestMethod.POST)
    public void otherGoodsList(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> params = getRequestParams(request);
        Pagination<GoodsVO> page = goodsService.otherGoodsList(params);
        printWriter(response,successResultJSON(page));

    }

}
