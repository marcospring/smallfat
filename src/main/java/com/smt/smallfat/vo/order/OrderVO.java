package com.smt.smallfat.vo.order;

import com.csyy.common.DateUtils;
import com.smt.smallfat.po.FatCustomerAddress;
import com.smt.smallfat.po.FatOrder;
import com.smt.smallfat.po.FatOrderItem;
import com.smt.smallfat.vo.GoodsVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderVO {
    private String nowTime = DateUtils.forDatetime(new Date());
    private FatOrder order;
    private FatCustomerAddress address;
    private List<OrderItem> items;

    public OrderVO() {
    }

    public OrderVO(FatOrder order, FatCustomerAddress address) {
        this.order = order;
        this.address = address;
    }

    public FatOrder getOrder() {
        return order;
    }

    public void setOrder(FatOrder order) {
        this.order = order;
    }

    public FatCustomerAddress getAddress() {
        return address;
    }

    public void setAddress(FatCustomerAddress address) {
        this.address = address;
    }

    public List<OrderItem> getItems() {
        if(items == null)
            items = new ArrayList<>();
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public class OrderItem {

        public OrderItem() {
        }

        public OrderItem(FatOrderItem orderItem, GoodsVO goods) {
            this.selectInfo = orderItem;
            this.goods = goods;
        }

        private FatOrderItem selectInfo;
        private GoodsVO goods;

        public FatOrderItem getSelectInfo() {
            return selectInfo;
        }

        public void setSelectInfo(FatOrderItem selectInfo) {
            this.selectInfo = selectInfo;
        }

        public GoodsVO getGoods() {
            return goods;
        }

        public void setGoods(GoodsVO goods) {
            this.goods = goods;
        }
    }
}

