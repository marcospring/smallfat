package com.smt.smallfat.vo.house;

import com.smt.smallfat.po.FatCustomer;
import com.smt.smallfat.po.FatSucculentFollow;

public class FollowVO {
    private FatCustomer customer;
    private FatSucculentFollow follow;

    public FollowVO() {
    }

    public FollowVO(FatCustomer customer, FatSucculentFollow follow) {
        this.customer = customer;
        this.follow = follow;
    }

    public FatCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(FatCustomer customer) {
        this.customer = customer;
    }

    public FatSucculentFollow getFollow() {
        return follow;
    }

    public void setFollow(FatSucculentFollow follow) {
        this.follow = follow;
    }
}
