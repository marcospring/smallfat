package com.smt.smallfat.vo;

import com.smt.smallfat.po.FatCustomer;

import java.util.List;

public class FavoriteUsersVO {
    private int favoriteCount;
    List<FatCustomer> users;

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public List<FatCustomer> getUsers() {
        return users;
    }

    public void setUsers(List<FatCustomer> users) {
        this.users = users;
    }
}
