package com.smt.smallfat.vo;

import com.smt.smallfat.po.FatCustomer;

public class UserVO {
    private FatCustomer user;
    private long praiseCount;
    private long followCount;
    private long beFollowedCount;

    public FatCustomer getUser() {
        return user;
    }

    public void setUser(FatCustomer user) {
        this.user = user;
    }

    public long getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(long praiseCount) {
        this.praiseCount = praiseCount;
    }

    public long getFollowCount() {
        return followCount;
    }

    public void setFollowCount(long followCount) {
        this.followCount = followCount;
    }

    public long getBeFollowedCount() {
        return beFollowedCount;
    }

    public void setBeFollowedCount(long beFollowedCount) {
        this.beFollowedCount = beFollowedCount;
    }
}
