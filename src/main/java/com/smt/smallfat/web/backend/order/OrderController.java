package com.smt.smallfat.web.backend.order;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.obj.Pagination;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.po.FatOrder;
import com.smt.smallfat.service.base.OrderService;
import com.smt.smallfat.service.pay.PayService;
import com.smt.smallfat.vo.order.OrderVO;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/backend/order")
public class OrderController extends BaseController{
    @Autowired
    private PayService payService;
    @Autowired
    private OrderService orderService;

    @RequestMapping("/refund")
    public void refund(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = nullAbleValidation(request, FatOrder.FIELD_ORDER_NO);
        String orderNo = StringDefaultValue.StringValue(param.get(FatOrder.FIELD_ORDER_NO));
        payService.refund(orderNo);
        printWriter(response,successResultJSON());
    }

    @RequestMapping("/orderList")
    public void orderList(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = getRequestParams(request);
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        Pagination<OrderVO> page = orderService.getOrderList(param,pageSize,pageNo);
        printWriter(response,successResultJSON(page));
     }

     @RequestMapping("/sendGoods")
     public void sendGoods(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> params = nullAbleValidation(request,FatOrder.FIELD_ORDER_NO,FatOrder.FIELD_FLEX_NO);
        String orderNo = StringDefaultValue.StringValue(params.get(FatOrder.FIELD_ORDER_NO));
        String flexNo = StringDefaultValue.StringValue(params.get(FatOrder.FIELD_FLEX_NO));
        orderService.sendGoods(orderNo,flexNo);
        printWriter(response,successResultJSON());
     }

    @RequestMapping("/handOrderOverdue")
    public void handOrderOverdue(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> params = nullAbleValidation(request,FatOrder.FIELD_ORDER_NO);
        String orderNo = StringDefaultValue.StringValue(params.get(FatOrder.FIELD_ORDER_NO));
        orderService.handOrderOverdue(orderNo);
        printWriter(response,successResultJSON());
    }
    @RequestMapping("/refuseRefund")
    public void refuseRefund(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> params = nullAbleValidation(request,FatOrder.FIELD_ORDER_NO);
        String orderNo = StringDefaultValue.StringValue(params.get(FatOrder.FIELD_ORDER_NO));
        orderService.refuseRefund(orderNo);
        printWriter(response,successResultJSON());
    }

}
