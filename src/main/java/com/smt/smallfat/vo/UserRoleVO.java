package com.smt.smallfat.vo;

/**
 * Created by zhaoruifeng on 16/8/16.
 */
public class UserRoleVO {
    private Integer id;
    private Integer userId;
    private  String roleName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
