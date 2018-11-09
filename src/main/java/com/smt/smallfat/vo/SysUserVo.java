package com.smt.smallfat.vo;

import java.util.Date;

/**
 * Created by xindongwang on 17/3/13.
 */
public class SysUserVo extends BaseVo {


    private static final long serialVersionUID = 2775412896720945971L;
    /**用户名*/
    private String  username = "";
    /**用户名 对应的静态变量值*/
    public static final String FIELD_USERNAME = "username";
    /**密码*/
    private String  password = "";
    /**密码 对应的静态变量值*/
    public static final String FIELD_PASSWORD = "password";
    /**真实姓名*/
    private String  realname = "";
    /**真实姓名 对应的静态变量值*/
    public static final String FIELD_REALNAME = "realname";
    /**手机*/
    private String  mobile = "";
    /**手机 对应的静态变量值*/
    public static final String FIELD_MOBILE = "mobile";
    /**QQ*/
    private String  qq = "";
    /**QQ 对应的静态变量值*/
    public static final String FIELD_QQ = "qq";
    /**微信号*/
    private String  wechat = "";
    /**微信号 对应的静态变量值*/
    public static final String FIELD_WECHAT = "wechat";
    /**电子邮件*/
    private String  email = "";
    /**电子邮件 对应的静态变量值*/
    public static final String FIELD_EMAIL = "email";
    /**ip地址*/
    private String  ipAddress = "";
    /**ip地址 对应的静态变量值*/
    public static final String FIELD_IP_ADDRESS = "ipAddress";
    /**最后登陆时间*/
    private Date lastLoginTime = new Date();
    /**最后登陆时间 对应的静态变量值*/
    public static final String FIELD_LAST_LOGIN_TIME = "lastLoginTime";
    /**状态 0启用 1禁用*/
    private Integer  status = 0;
    /**状态 0启用 1禁用 对应的静态变量值*/
    public static final String FIELD_STATUS = "status";
    /**
     * 角色
     */
    private String roles ;

    private int shopId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }
}
