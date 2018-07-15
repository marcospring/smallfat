package com.smt.smallfat.web.backend.goods;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.obj.Pagination;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.po.*;
import com.smt.smallfat.service.base.CategoryService;
import com.smt.smallfat.service.base.GoodsService;
import com.smt.smallfat.utils.push.IPush;
import com.smt.smallfat.utils.push.PushMessage;
import com.smt.smallfat.utils.push.PushPayloadBuilder;
import com.smt.smallfat.utils.push.pushenum.PlatForm;
import com.smt.smallfat.vo.BackendGoodsVO;
import com.smt.smallfat.vo.GoodsVO;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/backend/goods")
public class GoodsController extends BaseController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private IPush push;

    @RequestMapping("/addGoods")
    public void addGoods(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatGoods.FIELD_TITLE);
        FatGoods goodsVO = goodsService.addGoods(param);
        printWriter(response, successResultJSON(goodsVO));
    }

    @RequestMapping("/addGoodsResource")
    public void addGoodsResource(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatGoodsResource.FIELD_GOODS_ID, FatGoodsResource
                .FIELD_RESOURCE_TYPE, FatGoodsResource.FIELD_RESOURCE_URL);
        FatGoodsResource resource = goodsService.addGoodsResource(param);
        printWriter(response, successResultJSON(resource));
    }

    @RequestMapping("/addGoodsDetail")
    public void addGoodsDetail(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatGoodsDetail.FIELD_GOODS_ID);
        FatGoodsDetail detail = goodsService.addGoodsDetail(param);
        printWriter(response, successResultJSON(detail));
    }

    @RequestMapping("/getGoodsVoById")
    public void getGoodsVoById(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatGoods.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatGoods.FIELD_ID));
        GoodsVO vo = goodsService.getGoodsVO(id);
        printWriter(response, successResultJSON(vo));
    }

    @RequestMapping("/pageGoods")
    public void pageGoods(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = getRequestParams(request);
        Pagination<FatGoods> page = goodsService.pageGoods(param);
        printWriter(response, successResultJSON(page));
    }

    @RequestMapping("/deleteGoods")
    public void deleteGoods(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatGoods.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatGoods.FIELD_ID));
        goodsService.deleteGoods(id);
        printWriter(response, successResultJSON());
    }

    @RequestMapping("/deleteGoodsResource")
    public void deleteGoodsResource(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatGoodsResource.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatGoodsResource.FIELD_ID));
        goodsService.deleteGoodsResourceById(id);
        printWriter(response, successResultJSON());
    }

    @RequestMapping("/deleteGoodsDetail")
    public void deleteGoodsDetail(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatGoodsDetail.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatGoodsDetail.FIELD_ID));
        goodsService.deleteGoodsDetail(id);
        printWriter(response, successResultJSON());
    }

    @RequestMapping("/updateGoods")
    public void updateGoods(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatGoods.FIELD_ID);
        FatGoods goods = goodsService.updateGoods(param);
        printWriter(response, successResultJSON(goods));
    }

    @RequestMapping("/updateGoodsResource")
    public void updateGoodsResource(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatGoodsResource.FIELD_ID);
        FatGoodsResource resource = goodsService.updateGoodsResource(param);
        printWriter(response, successResultJSON(resource));
    }

    @RequestMapping("/updateGoodsDetail")
    public void updateGoodsDetail(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatGoodsDetail.FIELD_ID);
        FatGoodsDetail detail = goodsService.updateGoodsDetail(param);
        printWriter(response, successResultJSON(detail));
    }

    @RequestMapping("/getGoodsResourceByGoodsId")
    public void getGoodsResourceByGoodsId(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatGoodsResource.FIELD_GOODS_ID);
        int goodsId = StringDefaultValue.intValue(param.get(FatGoodsResource.FIELD_GOODS_ID));
        List<FatGoodsResource> resources = goodsService.getGoodsResourceByGoodsId(goodsId);
        printWriter(response, successResultJSON(resources));
    }

    @RequestMapping("/getGoodsDetailsByGoodsId")
    public void getGoodsDetailsByGoodsId(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatGoodsDetail.FIELD_GOODS_ID);
        int goodsId = StringDefaultValue.intValue(param.get(FatGoodsDetail.FIELD_GOODS_ID));
        List<FatGoodsDetail> details = goodsService.getGoodsDetailsByGoodsId(goodsId);
        printWriter(response, successResultJSON(details));
    }

    @RequestMapping("/pushGoods")
    public void pushGoods(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatGoods.FIELD_ID, FatGoods.FIELD_TITLE);
        String title = StringDefaultValue.StringValue(param.get(FatGoods.FIELD_TITLE));
        String id = StringDefaultValue.StringValue(param.get(FatGoods.FIELD_ID));
        PushMessage message = PushMessage.get().content(title).platform(PlatForm.ALL).title(title).addExtras(Constant
                .PUSH_TYPE, Constant.PushType.GOODS).addExtras(FatArticle.FIELD_TITLE, title).addExtras(FatArticle
                .FIELD_ID, id);
        push.push(PushPayloadBuilder.newInstance().build(message));
        printWriter(response, successResultJSON());
    }

    @RequestMapping("/addToApp")
    public void addToApp(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatGoods.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatGoods.FIELD_ID));
        goodsService.addToApp(id);
        printWriter(response, successResultJSON());
    }

    @RequestMapping("/orderGoods")
    public void orderGoods(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatGoods.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatGoods.FIELD_ID));
        goodsService.orderGoods(id);
        printWriter(response, successResultJSON());
    }

    @RequestMapping("/shoppingCartUsers")
    public void shoppingCartUsers(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatGoods.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatGoods.FIELD_ID));
        List<FatCustomer> customers = goodsService.shoppingCartUsers(id);
        printWriter(response, successResultJSON(customers));
    }

}
