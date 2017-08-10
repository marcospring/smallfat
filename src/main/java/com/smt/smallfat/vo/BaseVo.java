package com.smt.smallfat.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xindongwang on 17/3/12.
 */
public class BaseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    public static String FIELD_ID = "id";
    private String uuid;
    public static String FIELD_UUID = "uuid";
    private int createUser = 0;
    public static String FIELD_CREATE_USER = "createUser";
    private Date createTime;
    public static String FIELD_CREATE_TIME = "createTime";
    private int updateUser = 0;
    public static String FIELD_UPDATE_USER = "updateUser";
    private Date updateTime;
    public static String FIELD_UPDATE_TIME = "updateTime";
    private Integer disabled = Integer.valueOf(0);
    public static String FIELD_DISABLED = "disabled";
    private String remark = "";
    public static String FIELD_REMARK = "remark";
    private int orderBy;
    public static String FIELD_ORDER_BY = "orderBy";

    public BaseVo() { /* compiled code */ }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getCreateUser() {
        return createUser;
    }

    public void setCreateUser(int createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(int updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }
}
