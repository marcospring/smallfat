package com.smt.smallfat.vo;

/**
 * Created by xindongwang on 17/3/12.
 */
public class SysPermissionVo extends BaseVo {


    private static final long serialVersionUID = 4196506893619211040L;
    /**父权限ID*/
    private Integer  parentId = 0;
    /**父权限ID 对应的静态变量值*/
    public static final String FIELD_PARENT_ID = "parentId";
    /**权限名称*/
    private String  permissionName = "";
    /**权限名称 对应的静态变量值*/
    public static final String FIELD_PERMISSION_NAME = "permissionName";
    /**权限码*/
    private String  permissionCode = "";
    /**权限码 对应的静态变量值*/
    public static final String FIELD_PERMISSION_CODE = "permissionCode";
    /**权限路径*/
    private String  permissionUrl = "";
    /**权限路径 对应的静态变量值*/
    public static final String FIELD_PERMISSION_URL = "permissionUrl";
    /**权限类型 0菜单 1按钮*/
    private Integer  permissionType = 0;
    /**权限类型 0菜单 1按钮 对应的静态变量值*/
    public static final String FIELD_PERMISSION_TYPE = "permissionType";
    /**是否叶子节点*/
    private Integer  isLeaf = 0;
    /**是否叶子节点 对应的静态变量值*/
    public static final String FIELD_IS_LEAF = "isLeaf";
    /**系统ID*/
    private Integer  sysId = 0;
    /**系统ID 对应的静态变量值*/
    public static final String FIELD_SYS_ID = "sysId";
    /**状态 0启用 1禁用*/
    private Integer  status = 0;
    /**状态 0启用 1禁用 对应的静态变量值*/
    public static final String FIELD_STATUS = "status";

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionUrl() {
        return permissionUrl;
    }

    public void setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }

    public Integer getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Integer permissionType) {
        this.permissionType = permissionType;
    }

    public Integer getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Integer isLeaf) {
        this.isLeaf = isLeaf;
    }

    public Integer getSysId() {
        return sysId;
    }

    public void setSysId(Integer sysId) {
        this.sysId = sysId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
