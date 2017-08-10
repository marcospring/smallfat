package com.smt.smallfat.constant;

/**
 * Created by xindongwang on 17/3/12.
 */
public class ResultConstant {
    public interface SysDicResult{
        String SYSDIC_IS_NULL = "SYSDIC_IS_NULL";
        String SYSDICITEM_IS_NULL = "SYSDICITEM_IS_NULL";
        String SYSDIC_HAS_ITEMS="SYSDIC_HAS_ITEMS";
        String SYSDIC_ITEM_IS_LOCKED = "SYSDIC_ITEM_IS_LOCKED";
        String SYSDICITEM_IS_EXIST = "SYSDICITEM_IS_EXIST";
    }


    public interface SysUserResult{
        String SYSUSER_IS_NULL="SYSUSER_IS_NULL";
        String SYSUSER_IS_NOT_FOUND="SYSUSER_IS_NOT_FOUND";
        String SYSUSER_IS_EXISTS="SYSUSER_IS_EXISTS";
        String UPDATE_SYSUSER_IS_FAILD="UPDATE_SYSUSER_IS_FAILD";
        String MOBILE_IS_NULL="MOBILE_IS_NULL";
        String SYSUSER_ROLE_IS_NOT_EXISTS="SYSUSER_ROLE_IS_NOT_EXISTS";
        String INCORRECT_PASSWORD="INCORRECT_PASSWORD";
        String ADD_USER_FAILED = "ADD_USER_FAILED";
    }

    public interface SysRoleResult{
        String SYSROLE_IS_NULL = "SYSROLE_IS_NULL";
        String SYSROLE_IS_NOT_FOUND = "SYSROLE_IS_NOT_FOUND";
        String SYSROLE_IS_EXISTS="SYSROLE_IS_EXISTS";
        String SYSROLE_MIDDLE_IS_EXISTS="SYSROLE_MIDDLE_IS_EXISTS";
        String DELETE_SYSROLE_IS_FAILD = "DELETE_SYSROLE_IS_FAILD";
        String SYSROLE_ORVER_BD_ROLE_QUERY = "SYSROLE_ORVER_BD_ROLE_QUERY";
    }


    public interface SysPermission{
        String PERMISSION_IS_NULL = "PERMISSION_IS_NULL";
        String PERMISSION_IS_NOT_NULL = "PERMISSION_IS_NOT_NULL";
        String PERMISSION_HAS_CHILDREN = "PERMISSION_HAS_CHILDREN";
        String PARENT_PERMISSION_IS_NULL = "PARENT_PERMISSION_IS_NULL";
        String PERMISSION_HAS_USED = "PERMISSION_HAS_USED";
    }

    public interface SysAppVersion{
        String SYSAPPVER_IS_NULL = "SYSAPPVER_IS_NULL";
        String SYSAPPVER_IS_NOT_FOUND = "SYSAPPVER_IS_NOT_FOUND";
        String SYSAPPVER_IS_EXISTS="SYSAPPVER_IS_EXISTS";
        String SYSAPPVER_MIDDLE_IS_EXISTS="SYSAPPVER_MIDDLE_IS_EXISTS";
        String DELETE_SYSAPPVER_IS_FAILD = "DELETE_SYSAPPVER_IS_FAILD";
        String UPDATE_SYSAPPVER_IS_FAILD = "UPDATE_SYSAPPVER_IS_FAILD";
        String DOWNADDRESS_IS_NOT_FOUND = "DOWNADDRESS_IS_NOT_FOUND";
        String WORDS_IS_FULL = "WORDS_IS_FULL";

    }

    public interface CfgAuth{
        String CFG_AUTH_IS_LOCKED = "CFG_AUTH_IS_LOCKED";
        String CFG_AUTH_IS_EXITS = "CFG_AUTH_IS_EXITS";
    }

    public interface CfgCreditLineError{

        //已有相同的区间组合，请重新设置
        String CFG_CREDITLINE_ALREADY_EXIST = "CFG_CREDITLINE_ALREADY_EXIST";
        //配置区间不存在
        String CFG_CREDITLINE_NOT_EXIST = "CFG_CREDITLINE_NOT_EXIST";
        //区间状态不正确
        String CFG_CREDITLINE_STATUS_ERROR = "CFG_CREDITLINE_STATUS_ERROR";

    }

    public interface CfgRule{
        //规则不存在
        String CFG_RULE_NOT_EXIST = "CFG_RULE_NOT_EXIST";
        //修改失败
        String CFG_RULE_CHANGE_ERROR = "CFG_RULE_CHANGE_ERROR";

    }

    public interface channel{
        //渠道账号已存在
        String CHANNEL_NO_IS_EXIST = "CHANNEL_NO_IS_EXIST";
        //渠道名已存在
        String CHANNEL_NAME_IS_EXIST = "CHANNEL_NAME_IS_EXIST";
    }
}
