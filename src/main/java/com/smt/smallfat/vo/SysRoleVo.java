package com.smt.smallfat.vo;

/**
 * Created by xindongwang on 17/3/13.
 */
public class SysRoleVo extends BaseVo {

    private static final long serialVersionUID = 1133513440153401961L;
    /**表名*/
    public static final String tableName = "SYS_ROLE";
    /**角色名称*/
    private String  roleName = "";
    /**角色名称 对应的静态变量值*/
    public static final String FIELD_ROLE_NAME = "roleName";
    /**状态 0启用 1禁用*/
    private Integer  status = 0;
    /**状态 0启用 1禁用 对应的静态变量值*/
    public static final String FIELD_STATUS = "status";
    /**
     * 是否拥有
     */
    private String  tag;


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
