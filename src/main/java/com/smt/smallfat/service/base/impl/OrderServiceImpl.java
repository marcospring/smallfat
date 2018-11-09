package com.smt.smallfat.service.base.impl;

import com.csyy.common.JSONUtils;
import com.csyy.common.StringDefaultValue;
import com.csyy.constant.Constants;
import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.datasource.param.*;
import com.csyy.core.exception.CommonException;
import com.csyy.core.obj.Pagination;
import com.csyy.core.utils.CommonBeanUtils;
import com.csyy.redis.exception.DistributedLockException;
import com.csyy.redis.utils.lock.DistributedLock;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.exception.GetOrderLockException;
import com.smt.smallfat.exception.UnLockOrderException;
import com.smt.smallfat.po.*;
import com.smt.smallfat.service.base.*;
import com.smt.smallfat.utils.OrderNoCreator;
import com.smt.smallfat.utils.push.IPush;
import com.smt.smallfat.utils.push.PushMessage;
import com.smt.smallfat.utils.push.PushPayloadBuilder;
import com.smt.smallfat.utils.push.pushenum.PlatForm;
import com.smt.smallfat.vo.GoodsVO;
import com.smt.smallfat.vo.ShoppingCardVO;
import com.smt.smallfat.vo.order.OrderVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl extends BaseServiceImpl implements OrderService {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerAddressService customerAddressService;
    @Autowired
    private DistributedLock lock;
    @Autowired
    private IPush push;
    @Autowired
    NotificationService notificationService;
    @Autowired
    FreightService freightService;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ShoppingCardVO addToShoppingCart(Map<String, Object> param) {
        int goodsDetailId = StringDefaultValue.intValue(param.get(FatShoppingCart.FIELD_GOODS_DETAIL_ID));
        int goodsCount = StringDefaultValue.intValue(param.get(FatShoppingCart.FIELD_GOODS_COUNT));
        int userId = StringDefaultValue.intValue(param.get(FatShoppingCart.FIELD_USER_ID));
        int addressId = StringDefaultValue.intValue(param.get("addressId"));
        if (goodsCount <= 0)
            throw new CommonException(ResultConstant.Goods.GOODS_COUNT_MUST_UP_ZERO);

        Param params = ParamBuilder.getInstance().getParam();
        params.add(ParamBuilder.nv(FatShoppingCart.FIELD_USER_ID, userId));
        params.add(ParamBuilder.nv(FatShoppingCart.FIELD_GOODS_DETAIL_ID, goodsDetailId));
        FatShoppingCart cart = factory.getCacheReadDataSession().querySingleResultByParams(FatShoppingCart.class,
                params);
        if (cart != null) {
            try {
                isGoodsEnough(goodsDetailId, cart.getGoodsCount() + 1);
            } catch (CommonException e) {
                if (e.getExceptionKey().equals(ResultConstant.Order.GOODS_DETAIL_NOT_ENOUGH))
                    throw new CommonException(ResultConstant.Order.GOODS_ALREADY_IN_SHOPPING_CART);
            }

            cart.setGoodsCount(cart.getGoodsCount() + 1);
            cart.setGoodsTotalPrice(cart.getGoodsPrice().multiply(new BigDecimal(cart.getGoodsCount())));
            factory.getCacheWriteDataSession().update(FatShoppingCart.class, cart);
        } else {
            isGoodsEnough(goodsDetailId, goodsCount);
            FatGoodsDetail detail = goodsService.getGoodsDetailById(goodsDetailId);
            cart = CommonBeanUtils.transMap2BasePO(param, FatShoppingCart.class);
            cart.setGoodsPrice(detail.getPrice());
            cart.setGoodsTotalPrice(detail.getPrice().multiply(new BigDecimal(cart.getGoodsCount())));
            factory.getCacheWriteDataSession().save(FatShoppingCart.class, cart);
        }
        ShoppingCardVO result = fillShoppingCardVO(cart.getUserId(), addressId);
        return result;
    }

    private ShoppingCardVO fillShoppingCardVO(int userId, int addressId) {
        //查询用户购物车内容
        Param params = ParamBuilder.getInstance().getParam();
        params.add(ParamBuilder.nv(FatShoppingCart.FIELD_USER_ID, userId));
        List<FatShoppingCart> list = factory.getCacheReadDataSession().queryListResult(FatShoppingCart.class, params);
        ShoppingCardVO result = new ShoppingCardVO(list.size());
        BigDecimal postage = new BigDecimal(0);
        FatCustomerAddress address = null;
        if (addressId != 0)
            address = customerAddressService.getAddressById(addressId);
        //填充返回数据
        for (FatShoppingCart item : list) {
            FatGoodsDetail detail = goodsService.getGoodsDetailById(item.getGoodsDetailId());
            GoodsVO goodsVO = goodsService.getGoodsVO(detail.getGoodsId());

            ShoppingCardVO.ShoppingCardItem shoppingCardItem = result.new ShoppingCardItem(item.getId(), item
                    .getIsSelected(), goodsVO);
            ShoppingCardVO.ShoppingCardItem.CardItem cardItem = shoppingCardItem.new CardItem(item.getGoodsDetailId(), item
                    .getGoodsCount());
            //计算物品邮费
            if (address != null && item.getIsSelected() == 1) {
                BigDecimal goodsTotalWeight = detail.getWeight().multiply(new BigDecimal(item.getGoodsCount()));
                logger.info("======================================================");
                logger.info("goodsWeight:{},goodsCount:{}", detail.getWeight(), item.getGoodsCount());
                BigDecimal p = freightService.getPostage(address.getAreaCode(), goodsVO.getFreightHeadId(),
                        goodsTotalWeight);
                logger.info("=============>最外层邮费:{}", p);
                postage = postage.add(p);
            }
            logger.info("===========================================================");
            shoppingCardItem.setSelectInfo(cardItem);
            result.getItems().add(shoppingCardItem);
        }
        result.setPostage(postage);
        return result;
    }

    @Override
    public int isGoodsEnough(int goodsDetailId, int goodsCount) {
        FatGoodsDetail detail = goodsService.getGoodsDetailById(goodsDetailId);
        FatGoods goods = goodsService.getGoodsById(detail.getGoodsId());
        int count = detail.getStock();
        if (goodsCount <= 0)
            throw new CommonException(ResultConstant.Goods.GOODS_COUNT_MUST_UP_ZERO);
        if (count <= 0 || count - goodsCount < 0)
            throw new CommonException(ResultConstant.Order.GOODS_DETAIL_NOT_ENOUGH, detail.getModelSize(), goods.getTitle());
        return count;
    }

    @Override
    public void addOrderLock(int orderId, String operatorUUID) {
        CustomSQL where = SQLCreator.where();
        where.cloumn(FatOrder.FIELD_DISABLED).operator(ESQLOperator.EQ).v(Constant.WrapperExtend.ZERO).operator
                (ESQLOperator.AND).cloumn(FatOrder.FIELD_LUCK_LOCK).operator(ESQLOperator.EQ).v
                (StringDefaultValue.StringValue(Constant.WrapperExtend.ZERO))
                .operator(ESQLOperator.AND).cloumn(FatOrder.FIELD_ID).operator
                (ESQLOperator.EQ).v(orderId);
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatOrder.FIELD_LUCK_LOCK,
                operatorUUID));
        int updateCount = factory.getCacheWriteDataSession().updateCustomColumnByWhere(FatOrder.class, param, where);
        if (updateCount == 0)
            throw new GetOrderLockException(orderId);
    }

    @Override
    public void cancelOrderLock(int orderId, String operatorUUID) {
        CustomSQL where = SQLCreator.where();
        where.cloumn(FatOrder.FIELD_DISABLED).operator(ESQLOperator.EQ).v(Constant.WrapperExtend.ZERO).operator
                (ESQLOperator.AND).cloumn(FatOrder.FIELD_LUCK_LOCK).operator(ESQLOperator.EQ).v(operatorUUID)
                .operator(ESQLOperator.AND).cloumn(FatOrder.FIELD_ID).operator(ESQLOperator.EQ).v(orderId);
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatOrder.FIELD_LUCK_LOCK,
                StringDefaultValue.StringValue(Constant.WrapperExtend.ZERO)));
        int updateCount = factory.getCacheWriteDataSession().updateCustomColumnByWhere(FatOrder.class, param,
                where);
        if (updateCount == 0)
            throw new UnLockOrderException(orderId);
    }

    @Override
    public void addOrderHistory(FatOrder order) {
        FatOrderHistory history = FatOrderHistory.getInstance(FatOrderHistory.class);
        history.setOrderId(order.getId());
        history.setState(order.getState());
        history.setCreateUser(order.getCreateUser());
        history.setUpdateUser(order.getCreateUser());
        factory.getCacheWriteDataSession().save(FatOrderHistory.class, history);
    }

    @Override
    public int updateOrderStatus(int orderId, String operatorUUID, int orderStatus) {
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderVO minusStock(int customerId, int addressId, String memo) {
        Param params = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatShoppingCart.FIELD_USER_ID,
                customerId)).add(ParamBuilder.nv(FatShoppingCart.FIELD_IS_SELECTED, OrderService.CHECK));
        List<FatShoppingCart> list = factory.getCacheWriteDataSession().queryListResult(FatShoppingCart.class, params);
        if (list.size() == 0)
            throw new CommonException(ResultConstant.Order.SHOPPING_CART_EMPTY);
//        try {
        FatCustomerAddress address = factory.getCacheReadDataSession().querySingleResultById(FatCustomerAddress
                .class, addressId);
        List<OperateGoods> goodsList = fillOperateGoods(list, address);
        FatOrder order = buildOrder(customerId, goodsList, address, memo);
        OrderVO orderVO = new OrderVO(order, address);
        for (OperateGoods goods : goodsList) {
            List<OperateGoodsDetail> details = goods.getList();
            if (details != null && details.size() > 0) {
                for (OperateGoodsDetail detail : details) {
                    //加锁商品详情
                    // lock.lock(Constant.Lock.DETAIL_LOCK + customer.getUuid() + "_" + detail.getDetail().getId());
                    //  goodsService.addGoodsDetailLock(detail.getDetail().getId(), customer.getUuid());
                    //减库存
                    logger.info("减库存开始======================》");
                    CustomSQL where = SQLCreator.where();
                    where.cloumn(FatGoodsDetail.FIELD_STOCK).operator(ESQLOperator.MINUS).v(detail.getDetailCount())
                            .operator(ESQLOperator.GTEQ).v(Constant.WrapperExtend.ZERO).operator(ESQLOperator.AND).cloumn
                            (FatGoodsDetail.FIELD_ID).operator(ESQLOperator.EQ).v(detail.getDetail().getId());
                    Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatGoodsDetail.FIELD_STOCK,
                            detail.getDetail().getStock() - detail.getDetailCount()));
                    int count = factory.getCacheWriteDataSession().updateCustomColumnByWhere(FatGoodsDetail.class, param,
                            where);
                    if (count == 0)
                        throw new CommonException(ResultConstant.Goods.GOODS_DETAIL_IS_LOCKED);
                    FatOrderItem item = addOrderItem(order, detail);
                    orderVO.getItems().add(orderVO.new OrderItem(item, goodsService.getGoodsVoByGoodsDetailId(item.getGoodsDetailId())));
                    logger.info("减库存结束=======================>");
                    //解锁商品
                    //goodsService.cancelGoodsDetailLock(detail.getDetail().getId(), customer.getUuid());
                    //      lock.unLock(Constant.Lock.DETAIL_LOCK + customer.getUuid() + "_" + detail.getDetail().getId());
                }
            }
        }
        //清空购物车内商品
        for (FatShoppingCart cart : list) {
            factory.getCacheWriteDataSession().physicalDelete(FatShoppingCart.class, cart.getId());
        }
        //添加支付前数量锁定，放入redis   30分钟过期，过期后订单过期，商品归还
        //如果这句出异常数据也会回滚
        factory.getRedisSessionFactory().getSession().setData(Constant.ORDER_PREFIX + order.getOrderNo(), JSONUtils
                .toJson(goodsList), 30 * 60);
//        factory.getRedisSessionFactory().getSession().setData(Constant.ORDER_PREFIX + order.getOrderNo(), JSONUtils
//                .toJson(details), 1 * 60);

        return orderVO;
//        } catch (DistributedLockException e) {
////            goodsService.cancelGoodsDetailLock(e.getGoodsDetailId(), customer.getUuid());
////            FatGoodsDetail detail = goodsService.getGoodsDetailById(e.getGoodsDetailId());
////            FatGoods fatGoods = goodsService.getGoodsById(detail.getGoodsId());
//            throw new CommonException(ResultConstant.Goods.GOODS_DETAIL_IS_LOCKED);
//        }
    }

    //添加订单项
    private FatOrderItem addOrderItem(FatOrder order, OperateGoodsDetail detail) {
        FatOrderItem item = FatOrderItem.getInstance(FatOrderItem.class);
        item.setCustomerId(order.getCustomerId());
        item.setOrderId(order.getId());
        item.setPrice(detail.getDetail().getPrice());
        item.setTotalCount(detail.getDetailCount());
        item.setTotalPrice(detail.getTotalPrice());
        item.setTotalWeight(detail.getTotalWeight());
        item.setGoodsDetailId(detail.getDetail().getId());
        factory.getCacheWriteDataSession().save(FatOrderItem.class, item);
        return item;
    }

    private FatOrder buildOrder(int customerId, List<OperateGoods> goods, FatCustomerAddress address, String memo) {
        BigDecimal totalWeight = new BigDecimal(0);
        BigDecimal totalPrice = new BigDecimal(0);
        BigDecimal totalPostage = new BigDecimal(0);
        for (OperateGoods opGoods : goods) {
            List<OperateGoodsDetail> details = opGoods.getList();
            if (details != null && details.size() > 0) {
                for (OperateGoodsDetail detail : details) {
                    totalPrice = totalPrice.add(detail.getTotalPrice());
                    totalWeight = totalWeight.add(detail.getTotalWeight());

                }
            }
            totalPostage = totalPostage.add(opGoods.getTotalPostage());
        }
        FatOrder order = FatOrder.getInstance(FatOrder.class);
        order.setCustomerId(customerId);
        order.setOrderNo(OrderNoCreator.createOrderNo());
        order.setState(Constant.OrderStatus.ORDER_FOR_PAY);
        order.setTotalPrice(totalPrice);
        order.setTotalWeight(totalWeight);

        order.setUserAddress(new StringBuilder(address.getAreaAddress()).append(Constant.WrapperExtend.EMPTY)
                .append(address.getAddress()).toString());
        order.setAddressId(address.getId());
        order.setMemo(memo);
        order.setPostage(totalPostage);
        order = factory.getCacheWriteDataSession().save(FatOrder.class, order);
        addOrderHistory(order);

        return order;
    }

    private List<OperateGoods> fillOperateGoods(List<FatShoppingCart> items, FatCustomerAddress address) {
        List<OperateGoods> goodsList = new ArrayList<>();
        HashMap<Integer, OperateGoods> datas = new HashMap<>();
        //计算总重，总价
        for (FatShoppingCart item : items) {
            //判断库存是否足够
            isGoodsEnough(item.getGoodsDetailId(), item.getGoodsCount());
            FatGoodsDetail detail = goodsService.getGoodsDetailById(item.getGoodsDetailId());
            FatGoods goods = goodsService.getGoodsById(detail.getGoodsId());
            //设置操作的商品详情数据，总价，总重，总量
            OperateGoodsDetail operateGoodsDetail = new OperateGoodsDetail();
            operateGoodsDetail.setDetail(detail);
            operateGoodsDetail.setTotalPrice(detail.getPrice().multiply(new BigDecimal(item.getGoodsCount())));
            operateGoodsDetail.setTotalWeight(detail.getWeight().multiply(new BigDecimal(item.getGoodsCount())));
            operateGoodsDetail.setDetailCount(item.getGoodsCount());
            //将操作商品详情，放入操作商品中
            if (datas.containsKey(detail.getGoodsId())) {
                OperateGoods opGoods = datas.get(detail.getGoodsId());
                opGoods.addOperateDetail(operateGoodsDetail);
            } else {
                OperateGoods opGoods = new OperateGoods();
                opGoods.setGoods(goods);
                opGoods.addOperateDetail(operateGoodsDetail);
                datas.put(detail.getGoodsId(), opGoods);
            }
        }
        //设置邮费商品邮费，由于每个商品可能由不同的商户提供，有不同的运费，所以要按照商品计算邮费
        for (Integer goodsId : datas.keySet()) {
            OperateGoods op = datas.get(goodsId);
            List<OperateGoodsDetail> detailList = op.getList();
            BigDecimal goodsTotalWeight = new BigDecimal(0);
            if (detailList != null && detailList.size() > 0) {
                for (OperateGoodsDetail detail1 : detailList) {
                    goodsTotalWeight.add(detail1.getTotalWeight());
                }
            }
            BigDecimal postage = freightService.getPostage(address.getAreaCode(), op.getGoods().getFreightHeadId(),
                    goodsTotalWeight);
            op.setTotalPostage(postage);
            goodsList.add(op);
        }
        return goodsList;
    }


    @Override
    public ShoppingCardVO updateShoppingCart(Map<String, Object> param) {
        int userId = StringDefaultValue.intValue(param.get(Constant.USER_ID));
        String items = StringDefaultValue.StringValue(param.get(REQUEST_PARAM_ITEMS));
        int isCheck = StringDefaultValue.intValue(param.get(REQUEST_PARAM_IS_CHECK));
        int addressId = StringDefaultValue.intValue(param.get("addressId"));
        //更新用户购物车为全未选中状态
        updateUserShoppingCartNonSelect(userId);
        //处理信息
        String[] infos = items.split(Constant.Separator.COMMA);
        Param params = ParamBuilder.getInstance().getParam();
        for (String info : infos) {
            String[] is = info.split(Constant.DATA_SPECTOR);
            int detailId = StringDefaultValue.intValue(is[0]);
            int count = StringDefaultValue.intValue(is[1]);
            int isSelect = StringDefaultValue.intValue(is[2]);
            params.clean();
            params.add(ParamBuilder.nv(FatShoppingCart.FIELD_USER_ID, userId));
            params.add(ParamBuilder.nv(FatShoppingCart.FIELD_GOODS_DETAIL_ID, detailId));
            FatShoppingCart cartItem = factory.getReadDataSession().querySingleResultByParams(FatShoppingCart.class,
                    params);
            //如果操作为提交购物车数据，则要验证数库存，并设置数据是否被选中为生成订单的数据
            if (cartItem != null) {
                if (isCheck == CHECK) {
                    if (isSelect == 1)
                        //然后验证商品数量是否足够
                        isGoodsEnough(detailId, count);
                    //最后根据传入参数设置数据是否被选中
                    cartItem.setIsSelected(isSelect);
                }
                //设置购物车商品数量
                cartItem.setGoodsCount(count);
                factory.getCacheWriteDataSession().update(FatShoppingCart.class, cartItem);
            }
        }
        return fillShoppingCardVO(userId, addressId);
    }

    //首先更新所有用户购物车内数据为未选中状态
    private void updateUserShoppingCartNonSelect(int userId) {
        Param paramForShoppingCart = ParamBuilder.getInstance().getParam();
        paramForShoppingCart.add(ParamBuilder.nv(FatShoppingCart.FIELD_IS_SELECTED, Constant.WrapperExtend
                .ZERO));
        CustomSQL where = SQLCreator.where().cloumn(FatShoppingCart.FIELD_DISABLED).operator(ESQLOperator.EQ)
                .v(Constant.WrapperExtend.ZERO).operator(ESQLOperator.AND).cloumn(FatShoppingCart
                        .FIELD_USER_ID).operator(ESQLOperator.EQ).v(userId);
        factory.getCacheWriteDataSession().updateCustomColumnByWhere(FatShoppingCart.class,
                paramForShoppingCart, where);
    }

    @Override
    public ShoppingCardVO deleteShoppingCartItem(int id, int addressId) {
        FatShoppingCart cart = getShoppingCartById(id);
        int userId = cart.getUserId();
        factory.getCacheWriteDataSession().physicalDelete(FatShoppingCart.class, id);
        return fillShoppingCardVO(userId, addressId);
    }

    @Override
    public ShoppingCardVO getShoppingCardInfo(int userId, int addressId) {
        FatCustomerAddress address = customerAddressService.getUserDefaultAddress(userId);
        if (address != null)
            return fillShoppingCardVO(userId, address.getId());
        return fillShoppingCardVO(userId, addressId);
    }

    @Override
    public FatShoppingCart getShoppingCartById(int id) {
        FatShoppingCart cart = factory.getCacheReadDataSession().querySingleResultById(FatShoppingCart.class, id);
        if (cart == null)
            throw new CommonException(ResultConstant.Order.SHOPPING_CART_IS_NULL);
        return cart;
    }

    @Override
    public FatOrder getOrderByOrderNo(String orderNo) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatOrder.FIELD_ORDER_NO, orderNo));
        FatOrder order = factory.getReadDataSession().querySingleResultByParams(FatOrder.class, param);
        if (order == null)
            throw new CommonException(ResultConstant.Order.ORDER_IS_NULL);
        return order;
    }

    @Override
    public Pagination<OrderVO> getUserOrderList(int userId, int pageSize, int pageNo) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatOrder.FIELD_CUSTOMER_ID, userId))
                .add(ParamBuilder.nv(FatOrder.FIELD_DISABLED, Constant.WrapperExtend.ZERO));
        param.add(ParamBuilder.nv(Constant.SQLConstants.ORDER_BY_TYPE, Constant.SQLConstants.DESC));
        Pagination<FatOrder> page = queryClassPagination(FatOrder.class, param, pageNo, pageSize);
        List<FatOrder> data = page.getData();
        List<OrderVO> datavo = new ArrayList<>(data.size());
        for (FatOrder order : data) {
            datavo.add(getOrderVObyOrderNo(order.getOrderNo()));
        }
        Pagination<OrderVO> pageVO = new Pagination<>(datavo, page.getPageNo(), page.getPageSize());
        pageVO.setRecordsTotal(page.getRecordsTotal());

        return pageVO;
    }

    @Override
    public OrderVO getOrderVObyOrderNo(String orderNo) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatOrder.FIELD_ORDER_NO, orderNo));
        FatOrder order = getOrderByOrderNo(orderNo);
        param.clean().add(ParamBuilder.nv(FatOrderItem.FIELD_ORDER_ID, order.getId()));
        List<FatOrderItem> items = factory.getCacheReadDataSession().queryListResult(FatOrderItem.class, param);
        OrderVO vo = new OrderVO(order, customerAddressService.getAddressById(order.getAddressId()));
        for (FatOrderItem item : items) {
            GoodsVO goodsVO = goodsService.getGoodsVoByGoodsDetailId(item.getGoodsDetailId());
            OrderVO.OrderItem itemVo = vo.new OrderItem(item, goodsVO);
            vo.getItems().add(itemVo);
        }
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderVO refund(String orderNo, int userId) {
        FatOrder order = getOrderByOrderNo(orderNo);
        FatCustomer customer = customerService.getCustomerById(userId);
        try {
            //addOrderLock(order.getId(), customer.getUuid());
            lock.lock(Constant.Lock.ORDER_LOCK + order.getId() + "_" + customer.getUuid());
            //判断订单是否发货
            order = getOrderByOrderNo(orderNo);
            if (order.getState() == Constant.OrderStatus.PAY_FOR_BACK)
                throw new CommonException(ResultConstant.Order.ALREADY_REFUND);
            if (order.getState() == Constant.OrderStatus.ALREADY_SEND)
                throw new CommonException(ResultConstant.Order.ORDER_ALREADY_SEND);
            order.setState(Constant.OrderStatus.PAY_FOR_BACK);
            factory.getCacheWriteDataSession().update(FatOrder.class, order);
            addOrderHistory(order);
            // cancelOrderLock(order.getId(), customer.getUuid());
            lock.unLock(Constant.Lock.ORDER_LOCK + order.getId() + "_" + customer.getUuid());
        } catch (DistributedLockException e) {
            throw new CommonException(ResultConstant.Order.ORDER_IS_LOCKED);
        }

        return new OrderVO(order, customerAddressService.getAddressById(order.getAddressId()));
    }

    @Override
    public Pagination<OrderVO> getOrderList(Map<String, Object> param, int pageSize, int pageNo) {
        Param params = ParamBuilder.getInstance().getParam().add(param);
        params.add(ParamBuilder.nv(Constant.SQLConstants.ORDER_BY_TYPE, Constant.SQLConstants.DESC));
        Pagination<FatOrder> page = queryClassPagination(FatOrder.class, params, pageNo, pageSize);
        List<FatOrder> data = page.getData();
        List<OrderVO> datavo = new ArrayList<>(data.size());
        for (FatOrder order : data) {
            datavo.add(getOrderVObyOrderNo(order.getOrderNo()));
        }
        Pagination<OrderVO> pageVO = new Pagination<>(datavo, page.getPageNo(), page.getPageSize());
        pageVO.setRecordsTotal(page.getRecordsTotal());
        return pageVO;
    }

    @Override
    public void sendGoods(String orderNo, String flexNo) {
        try {
            FatOrder order = getOrderByOrderNo(orderNo);
            FatCustomer customer = customerService.getCustomerById(order.getCustomerId());
            //因为发货和用户提起退款要同时锁定，所以锁定key是一致的
            lock.lock(Constant.Lock.ORDER_LOCK + order.getId() + "_" + customer.getUuid());
            order = getOrderByOrderNo(orderNo);
            if (order.getState() != Constant.OrderStatus.ALREADY_PAY)
                throw new CommonException(ResultConstant.Order.ORDER_IS_ALREADY_PAY_FOR_BACK);
            order.setState(Constant.OrderStatus.ALREADY_SEND);
            order.setFlexNo(flexNo);
            factory.getCacheWriteDataSession().update(FatOrder.class, order);
            addOrderHistory(order);
            //发货推送
            //String messageStr = "亲爱的,{0}，您订单编号为：{1}货物已发出，申通快递订单号为：{2}.您可以到申通查询送件进度。";

            String messageContent = PushMessage.buildMessage(order.getFlexNo(), customer.getNickName(), order.getOrderNo(),
                    order.getFlexNo());
            FatNotification notification = FatNotification.getInstance(FatNotification.class);
            notification.setIsRead(0);
            notification.setUserId(customer.getId());
            notification.setTitle("发货通知");
            notification.setContent(messageContent);
            notification.setNickName(customer.getNickName());
            notificationService.addNotification(notification);

            PushMessage message = PushMessage.get().content(messageContent).platform(PlatForm.IOS).title(messageContent)
                    .addExtras(Constant.PUSH_TYPE, Constant.PushType.DELIVER_GOODS).addExtras(FatOrder.FIELD_ORDER_NO, order
                            .getOrderNo()).addAlias(customer.getUuid());
            push.push(PushPayloadBuilder.newInstance().build(message));
            // cancelOrderLock(order.getId(), customer.getUuid());
            lock.unLock(Constant.Lock.ORDER_LOCK + order.getId() + "_" + customer.getUuid());
        } catch (DistributedLockException e) {
            throw new CommonException(ResultConstant.Order.ORDER_IS_LOCKED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void orderOverdueCallBack(String message) {
//        RedisSession session = factory.getRedisSessionFactory().getSession();
//        session.subscribe(new MsgHandler(message1 -> System.out.println(message1.getMsg())), Constant
//                .REDIS_KEY_OVERDUE_CHANEL);
        if (!message.startsWith(Constant.ORDER_PREFIX)) {
            return;
        }
        String orderNO = message.replace(Constant.ORDER_PREFIX, "");
        handOrderOverdue(orderNO);
//        OrderVO vo = getOrderVObyOrderNo(orderNO);
//        if (vo.getOrder().getState() != Constant.OrderStatus.ORDER_FOR_PAY)
//            return;
//        List<OrderVO.OrderItem> items = vo.getItems();
//        for (OrderVO.OrderItem item : items) {
//            //还回购物车
//            FatShoppingCart cart = FatShoppingCart.getInstance(FatShoppingCart.class);
//            cart.setGoodsTotalPrice(item.getSelectInfo().getTotalPrice());
//            cart.setGoodsPrice(item.getSelectInfo().getPrice());
//            cart.setIsSelected(1);
//            cart.setGoodsCount(item.getSelectInfo().getTotalCount());
//            cart.setGoodsDetailId(item.getSelectInfo().getGoodsDetailId());
//            cart.setUserId(vo.getOrder().getCustomerId());
//            factory.getCacheWriteDataSession().save(FatShoppingCart.class, cart);
//            logger.info("退还购物车：{}", JSONUtils.toJson(cart));
//            //退还库存
//            logger.info("超时退库开始======》");
//            goodsService.returnInventory(item.getSelectInfo().getGoodsDetailId(), item.getSelectInfo().getTotalCount());
//            logger.info("超时退库结束=====》");
//            //删除订单详情
//            // factory.getCacheWriteDataSession().physicalDelete(FatOrderItem.class, item.getSelectInfo().getId());
//        }
//        //删除订单
//        //factory.getCacheWriteDataSession().physicalDelete(FatOrder.class, vo.getOrder().getId());
//        //设置订单为订单过期
//        FatOrder order = vo.getOrder();
//        order.setState(Constant.OrderStatus.ORDER_DELAY);
//        addOrderHistory(order);
//        factory.getCacheWriteDataSession().update(FatOrder.class, order);

        logger.info(message);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handOrderOverdue(String orderNo) {
        OrderVO vo = getOrderVObyOrderNo(orderNo);
        if (vo.getOrder().getState() != Constant.OrderStatus.ORDER_FOR_PAY)
            return;
        List<OrderVO.OrderItem> items = vo.getItems();
        for (OrderVO.OrderItem item : items) {
            //还回购物车
            FatShoppingCart cart = FatShoppingCart.getInstance(FatShoppingCart.class);
            cart.setGoodsTotalPrice(item.getSelectInfo().getTotalPrice());
            cart.setGoodsPrice(item.getSelectInfo().getPrice());
            cart.setIsSelected(1);
            cart.setGoodsCount(item.getSelectInfo().getTotalCount());
            cart.setGoodsDetailId(item.getSelectInfo().getGoodsDetailId());
            cart.setUserId(vo.getOrder().getCustomerId());
            factory.getCacheWriteDataSession().save(FatShoppingCart.class, cart);
            logger.info("退还购物车：{}", JSONUtils.toJson(cart));
            //退还库存
            logger.info("超时退库开始======》");
            goodsService.returnInventory(item.getSelectInfo().getGoodsDetailId(), item.getSelectInfo().getTotalCount());
            logger.info("超时退库结束=====》");
            //删除订单详情
            // factory.getCacheWriteDataSession().physicalDelete(FatOrderItem.class, item.getSelectInfo().getId());
        }
        //删除订单
        //factory.getCacheWriteDataSession().physicalDelete(FatOrder.class, vo.getOrder().getId());
        //设置订单为订单过期
        FatOrder order = vo.getOrder();
        order.setState(Constant.OrderStatus.ORDER_DELAY);
        addOrderHistory(order);
        factory.getCacheWriteDataSession().update(FatOrder.class, order);
    }

    @Override
    public List<FatOrderItem> getOrderItemListByOrderId(int orderId) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatOrderItem.FIELD_ORDER_ID, orderId));
        List<FatOrderItem> list = factory.getCacheReadDataSession().queryListResult(FatOrderItem.class, param);
        return list;
    }

    @Override
    public void cusDelOrder(String orderNo) {
        FatOrder order = getOrderByOrderNo(orderNo);
        if (order.getState() != Constant.OrderStatus.ALREADY_PAY_FOR_BACK && order.getState() != Constant.OrderStatus
                .ORDER_DELAY)
            throw new CommonException(ResultConstant.Order.ORDER_STATUS_INVALID);
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatOrder.FIELD_ID, order.getId()));
        factory.getCacheWriteDataSession().logicDelete(FatOrder.class, param);
    }

    @Override
    public void refuseRefund(String orderNo) {
        FatOrder order = getOrderByOrderNo(orderNo);
        order.setState(Constant.OrderStatus.ALREADY_PAY);
        factory.getCacheWriteDataSession().update(FatOrder.class, order);
        addOrderHistory(order);
    }
}

class OperateGoodsDetail {
    private FatGoodsDetail detail;
    private BigDecimal totalPrice;
    private BigDecimal totalWeight;
    private int detailCount;

    public int getDetailCount() {
        return detailCount;
    }

    public void setDetailCount(int detailCount) {
        this.detailCount = detailCount;
    }

    public FatGoodsDetail getDetail() {
        return detail;
    }

    public void setDetail(FatGoodsDetail detail) {
        this.detail = detail;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(BigDecimal totalWeight) {
        this.totalWeight = totalWeight;
    }


}

class OperateGoods {
    private FatGoods goods;
    private List<OperateGoodsDetail> list;
    private BigDecimal totalPostage;

    public BigDecimal getTotalPostage() {
        return totalPostage;
    }

    public void setTotalPostage(BigDecimal totalPostage) {
        this.totalPostage = totalPostage;
    }

    public FatGoods getGoods() {
        return goods;
    }

    public void setGoods(FatGoods goods) {
        this.goods = goods;
    }

    public List<OperateGoodsDetail> getList() {
        return list;
    }

    public void setList(List<OperateGoodsDetail> list) {
        this.list = list;
    }

    public void addOperateDetail(OperateGoodsDetail detail) {
        if (this.list == null)
            list = new ArrayList<>();
        list.add(detail);
    }
}