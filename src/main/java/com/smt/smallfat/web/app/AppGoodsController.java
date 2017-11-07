package com.smt.smallfat.web.app;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.obj.Pagination;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.po.FatGoods;
import com.smt.smallfat.service.GoodsService;
import com.smt.smallfat.vo.GoodsVO;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/app/goods")
public class AppGoodsController extends BaseController {

    @Autowired
    private GoodsService goodsService;


    @RequestMapping("/goodsList")
    public void goodsList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = getRequestParams(request);
        Pagination<GoodsVO> list = goodsService.pageGoodsVO(params);
        printWriter(response, successResultJSON(list));
    }

    @RequestMapping("/viewGoods")
    public void viewGoods(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatGoods.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatGoods.FIELD_ID));
        GoodsVO vo = goodsService.getGoodsVO(id);
        vo.setShareUrl(Constant.GOODS_SHARE_URL + id);
        printWriter(response, successResultJSON(vo));
    }


}
