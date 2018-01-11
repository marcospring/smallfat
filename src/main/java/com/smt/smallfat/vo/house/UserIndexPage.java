package com.smt.smallfat.vo.house;

import com.csyy.core.obj.Pagination;
import com.smt.smallfat.po.FatCustomer;

public class UserIndexPage {
    private int isFollow;
    private FatCustomer customer;
    private long followCount;
    private long followedCount;
    private long praiseCount;
    private Pagination<FlowerHouseItemVO> pageCircle;

    public int getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }

    public FatCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(FatCustomer customer) {
        this.customer = customer;
    }

    public long getFollowCount() {
        return followCount;
    }

    public void setFollowCount(long followCount) {
        this.followCount = followCount;
    }

    public long getFollowedCount() {
        return followedCount;
    }

    public void setFollowedCount(long followedCount) {
        this.followedCount = followedCount;
    }

    public long getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(long praiseCount) {
        this.praiseCount = praiseCount;
    }

    public Pagination<FlowerHouseItemVO> getPageCircle() {
        return pageCircle;
    }

    public void setPageCircle(Pagination<FlowerHouseItemVO> pageCircle) {
        this.pageCircle = pageCircle;
    }
}
