package com.smt.smallfat.vo;

/**
 * Created by xindongwang on 17/3/13.
 */
public class SysUserRoleVo extends BaseVo {
    private static final long serialVersionUID = -6573362536004678523L;
    /**表名*/
    public static final String tableName = "SYS_USER_ROLE";
    /**用户ID*/
    private Integer  userId = 0;
    /**用户ID 对应的静态变量值*/
    public static final String FIELD_USER_ID = "userId";
    /**角色ID*/
    private Integer  roleId = 0;
    /**角色ID 对应的静态变量值*/
    public static final String FIELD_ROLE_ID = "roleId";
    /**状态*/
    private Integer  status = 0;
    /**状态 对应的静态变量值*/
    public static final String FIELD_STATUS = "status";

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
