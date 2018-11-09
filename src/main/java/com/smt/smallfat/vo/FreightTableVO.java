package com.smt.smallfat.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FreightTableVO {
    private int tableId;
    private String type;
    private Date updateTime;
    private String shopName;
    private String FexName;
    private List<FreightTableItemVO> items;

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getFexName() {
        return FexName;
    }

    public void setFexName(String fexName) {
        FexName = fexName;
    }

    public List<FreightTableItemVO> getItems() {
        return items;
    }

    public void setItems(List<FreightTableItemVO> items) {
        this.items = items;
    }

    public void addItem(FreightTableItemVO item){
        if(items == null)
            items = new ArrayList<>();
        items.add(item);
    }
}
