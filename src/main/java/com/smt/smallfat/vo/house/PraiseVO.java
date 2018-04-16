package com.smt.smallfat.vo.house;

import com.smt.smallfat.po.FatCustomer;
import com.smt.smallfat.po.FatSucculentImage;
import com.smt.smallfat.po.FatSucculentPraise;

public class PraiseVO {
    private FatCustomer customer;
    private FatSucculentImage image;
    private FatSucculentPraise praise;

    public PraiseVO() {
    }

    public PraiseVO(FatCustomer customer, FatSucculentImage image, FatSucculentPraise praise) {
        this.customer = customer;
        this.image = image;
        this.praise = praise;
    }

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

    public FatSucculentPraise getPraise() {
        return praise;
    }

    public void setPraise(FatSucculentPraise praise) {
        this.praise = praise;
    }
}
