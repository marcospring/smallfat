package com.smt.smallfat.vo.house;

import com.smt.smallfat.po.FatCustomer;
import com.smt.smallfat.po.FatSucculentImage;

public class PraiseUserVO {
    private FatCustomer customer;
    private FatSucculentImage image;
    private int circleId;

    public FatCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(FatCustomer customer) {
        this.customer = customer;
    }

    public FatSucculentImage getImage() {
        return image;
    }

    public void setImage(FatSucculentImage image) {
        this.image = image;
    }

    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }
}
