package com.smt.smallfat.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCardVO {
    private int count;
    private BigDecimal postage = BigDecimal.ZERO;
    private List<ShoppingCardItem> items;

    public ShoppingCardVO() {
    }

    public BigDecimal getPostage() {
        return postage;
    }

    public void setPostage(BigDecimal postage) {
        this.postage = postage;
    }

    public ShoppingCardVO(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ShoppingCardItem> getItems() {
        if(items == null){
            if(this.count > 0)
                items = new ArrayList<>(count);
            else
                items = new ArrayList<>();
        }

        return items;
    }

    public void setItems(List<ShoppingCardItem> items) {
        this.items = items;
    }

    public class ShoppingCardItem {
        private int shoppingCardId;
        private int isSelected;
        private GoodsVO goods;
        private CardItem selectInfo;

        public ShoppingCardItem() {
        }

        public ShoppingCardItem(int shoppingCardId,int isSelected, GoodsVO goods) {
            this.shoppingCardId = shoppingCardId;
            this.goods = goods;
            this.isSelected = isSelected;
        }

        public int getIsSelected() {
            return isSelected;
        }

        public int getShoppingCardId() {
            return shoppingCardId;
        }

        public void setShoppingCardId(int shoppingCardId) {
            this.shoppingCardId = shoppingCardId;
        }

        public void setIsSelected(int isSelected) {
            this.isSelected = isSelected;
        }

        public GoodsVO getGoods() {
            return goods;
        }

        public void setGoods(GoodsVO goods) {
            this.goods = goods;
        }

        public CardItem getSelectInfo() {
            return selectInfo;
        }

        public void setSelectInfo(CardItem selectInfo) {
            this.selectInfo = selectInfo;
        }

        public  class CardItem {
            int detailId;
            int buyCount;

            public CardItem() {
            }

            public CardItem(int detailId, int buyCount) {
                this.detailId = detailId;
                this.buyCount = buyCount;
            }

            public int getDetailId() {
                return detailId;
            }

            public void setDetailId(int detailId) {
                this.detailId = detailId;
            }

            public int getBuyCount() {
                return buyCount;
            }

            public void setBuyCount(int buyCount) {
                this.buyCount = buyCount;
            }
        }
    }
}