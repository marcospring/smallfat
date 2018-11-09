package com.smt.smallfat.vo;

import com.smt.smallfat.po.FatArea;

import java.math.BigDecimal;
import java.util.List;

public class FreightTableItemVO {
    private int itemId;
    private int tableId;
    private BigDecimal firstCount;
    private BigDecimal firstPrice;
    private BigDecimal otherCount;
    private BigDecimal otherPrice;
    private List<FatArea> areas;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public BigDecimal getFirstCount() {
        return firstCount;
    }

    public void setFirstCount(BigDecimal firstCount) {
        this.firstCount = firstCount;
    }

    public BigDecimal getFirstPrice() {
        return firstPrice;
    }

    public void setFirstPrice(BigDecimal firstPrice) {
        this.firstPrice = firstPrice;
    }

    public BigDecimal getOtherCount() {
        return otherCount;
    }

    public void setOtherCount(BigDecimal otherCount) {
        this.otherCount = otherCount;
    }

    public BigDecimal getOtherPrice() {
        return otherPrice;
    }

    public void setOtherPrice(BigDecimal otherPrice) {
        this.otherPrice = otherPrice;
    }

    public List<FatArea> getAreas() {
        return areas;
    }

    public void setAreas(List<FatArea> areas) {
        this.areas = areas;
    }
}
