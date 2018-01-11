package com.smt.smallfat.service.base;

import com.csyy.core.obj.Pagination;
import com.smt.smallfat.exception.GetOrderLockException;
import com.smt.smallfat.exception.UnLockOrderException;
import com.smt.smallfat.po.FatGoodsDetail;
import com.smt.smallfat.po.FatOrder;
import com.smt.smallfat.po.FatOrderItem;
import com.smt.smallfat.po.FatShoppingCart;
import com.smt.smallfat.vo.ShoppingCardVO;
import com.smt.smallfat.vo.order.OrderVO;

import java.util.List;
import java.util.Map;

public interface OrderService {

     String REQUEST_PARAM_ITEMS="items";
     String REQUEST_PARAM_IS_CHECK = "isCheck";
    /**
     * 是否为购物车提交：
     * 1：提交（验证库存）
     * 0：更新（不验证库存，直接更新购物车）
     */
    int UN_CHECK = 0;
     int CHECK = 1;

    /**
     * 加入购物车
     * @param param
     */
    ShoppingCardVO addToShoppingCart(Map<String,Object> param);

    void addOrderLock(int orderId,String operatorUUID) throws GetOrderLockException;

    void cancelOrderLock(int orderId,String operatorUUID) throws UnLockOrderException;

    void addOrderHistory(FatOrder order);

    int updateOrderStatus(int orderId,String operatorUUID,int orderStatus);

    int isGoodsEnough(int goodsDetailId, int goodsCount);

    FatShoppingCart getShoppingCartById(int id);

    /**
     * 减库存,生成订单
     */
    OrderVO minusStock(int customerId,int addressId,String memo);

    ShoppingCardVO updateShoppingCart(Map<String,Object> param);

    ShoppingCardVO deleteShoppingCartItem(int id);

    ShoppingCardVO getShoppingCardInfo(int userId);

    FatOrder getOrderByOrderNo(String orderNo);

    Pagination<OrderVO> getUserOrderList(int userId,int pageSize,int pageNo);

    OrderVO getOrderVObyOrderNo(String orderNo);

    /**
     * 申请退款
     * @param orderNo
     */
    OrderVO refund(String orderNo,int userId);

    Pagination<OrderVO> getOrderList(Map<String,Object> param,int pageSize,int pageNo);

    void sendGoods(String orderNo,String flexNo);

    void orderOverdueCallBack(String message);

    /**
     * 订单人工退库
     * @param orderNo
     */
    void handOrderOverdue(String orderNo);

    List<FatOrderItem> getOrderItemListByOrderId(int orderId);

    void cusDelOrder(String orderNo);

}
