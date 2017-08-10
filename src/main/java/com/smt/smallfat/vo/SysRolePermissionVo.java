package com.smt.smallfat.vo;

import java.io.Serializable;

/**
 * Created by xindongwang on 17/3/13.
 */
public class SysRolePermissionVo implements Serializable{

    private static final long serialVersionUID = 9200459090532950345L;
    /**表名*/
    public static final String tableName = "SYS_ROLE_PERMISSION";
    /***/
    private Integer  roleId = 0;
    /** 对应的静态变量值*/
    public static final String FIELD_ROLE_ID = "roleId";
    /***/
    private Integer  permissionId = 0;
    /** 对应的静态变量值*/
    public static final String FIELD_PERMISSION_ID = "permissionId";

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }
}
