package com.smt.smallfat.web.app;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.obj.Pagination;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.po.FatOrder;
import com.smt.smallfat.po.FatShoppingCart;
import com.smt.smallfat.service.OrderService;
import com.smt.smallfat.vo.ShoppingCardVO;
import com.smt.smallfat.vo.order.OrderVO;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/app/order")
public class AppOrderController extends BaseController {

    @Autowired
    private OrderService orderService;


    @RequestMapping("/addToShoppingCart")
    public void addToShoppingCart(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, Constant.USER_ID, FatShoppingCart
                .FIELD_GOODS_COUNT, FatShoppingCart.FIELD_GOODS_DETAIL_ID);
        ShoppingCardVO result = orderService.addToShoppingCart(param);
        printWriter(response, successResultJSON(result));
    }

    @RequestMapping("/isGoodsEnough")
    public void isGoodsEnough(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatShoppingCart.FIELD_GOODS_DETAIL_ID, FatShoppingCart
                .FIELD_GOODS_COUNT);
        int id = StringDefaultValue.intValue(param.get(FatShoppingCart.FIELD_GOODS_DETAIL_ID));
        int count = StringDefaultValue.intValue(param.get(FatShoppingCart.FIELD_GOODS_COUNT));
        int lastCount = orderService.isGoodsEnough(id, count);
        printWriter(response, successResultJSON(lastCount));
    }

    @RequestMapping("/shoppingCartOK")
    public void shoppingCartOK(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, Constant.USER_ID, FatOrder.FIELD_ADDRESS_ID);
        int userId = StringDefaultValue.intValue(param.get(Constant.USER_ID));
        int addressId = StringDefaultValue.intValue(param.get(FatOrder.FIELD_ADDRESS_ID));
        String memo = StringDefaultValue.StringValue(param.get(FatOrder.FIELD_MEMO));
        OrderVO orderVO = orderService.minusStock(userId, addressId, memo);
        //提交支付
        printWriter(response, successResultJSON(orderVO));
    }

    @RequestMapping("/updateShoppingCart")
    public void updateShoppingCart(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, OrderService.REQUEST_PARAM_IS_CHECK,
                OrderService.REQUEST_PARAM_ITEMS, Constant
                        .USER_ID);
        ShoppingCardVO result = orderService.updateShoppingCart(param);
        printWriter(response, successResultJSON(result));
    }

    @RequestMapping("/getShoppingCardInfo")
    public void getShoppingCardInfo(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, Constant.USER_ID);
        int userId = StringDefaultValue.intValue(param.get(Constant.USER_ID));
        ShoppingCardVO result = orderService.getShoppingCardInfo(userId);
        printWriter(response, successResultJSON(result));
    }

    @RequestMapping("/deleteShoppingCardItem")
    public void deleteShoppingCardItem(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatShoppingCart.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatShoppingCart.FIELD_ID));
        ShoppingCardVO result = orderService.deleteShoppingCartItem(id);
        printWriter(response, successResultJSON(result));
    }

    @RequestMapping("/userOrderList")
    public void userOrderList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, Constant.USER_ID);
        int userId = StringDefaultValue.intValue(param.get(Constant.USER_ID));
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        Pagination<OrderVO> list = orderService.getUserOrderList(userId, pageSize, pageNo);
        printWriter(response, successResultJSON(list));
    }

    @RequestMapping("/orderView")
    public void orderView(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatOrder.FIELD_ORDER_NO);
        String orderNO = StringDefaultValue.StringValue(param.get(FatOrder.FIELD_ORDER_NO));
        OrderVO orderVO = orderService.getOrderVObyOrderNo(orderNO);
        printWriter(response, successResultJSON(orderVO));
    }

    @RequestMapping("/refund")
    public void refund(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = nullAbleValidation(request,FatOrder.FIELD_ORDER_NO,Constant.USER_ID);
        String orderNo = StringDefaultValue.StringValue(param.get(FatOrder.FIELD_ORDER_NO));
        int userId = StringDefaultValue.intValue(param.get(Constant.USER_ID));
        OrderVO order =orderService.refund(orderNo, userId);
        printWriter(response,successResultJSON(order));
    }

    @RequestMapping("/cusDelOrder")
    public void cusDelOrder(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = nullAbleValidation(request,FatOrder.FIELD_ORDER_NO);
        String orderNo = StringDefaultValue.StringValue(param.get(FatOrder.FIELD_ORDER_NO));
        orderService.cusDelOrder(orderNo);
        printWriter(response,successResultJSON());
    }
}
