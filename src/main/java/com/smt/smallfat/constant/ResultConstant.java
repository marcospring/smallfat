package com.smt.smallfat.constant;

/**
 * Created by xindongwang on 17/3/12.
 */
public class ResultConstant {
    public interface SysDicResult {
        String SYSDIC_IS_NULL = "SYSDIC_IS_NULL";
        String SYSDICITEM_IS_NULL = "SYSDICITEM_IS_NULL";
        String SYSDIC_HAS_ITEMS = "SYSDIC_HAS_ITEMS";
        String SYSDIC_ITEM_IS_LOCKED = "SYSDIC_ITEM_IS_LOCKED";
        String SYSDICITEM_IS_EXIST = "SYSDICITEM_IS_EXIST";
    }


    public interface SysUserResult {
        String LOGIN_ERROR="LOGIN_ERROR";
        String SYSUSER_IS_NULL = "SYSUSER_IS_NULL";
        String SYSUSER_IS_NOT_FOUND = "SYSUSER_IS_NOT_FOUND";
        String SYSUSER_IS_EXISTS = "SYSUSER_IS_EXISTS";
        String UPDATE_SYSUSER_IS_FAILD = "UPDATE_SYSUSER_IS_FAILD";
        String MOBILE_IS_NULL = "MOBILE_IS_NULL";
        String SYSUSER_ROLE_IS_NOT_EXISTS = "SYSUSER_ROLE_IS_NOT_EXISTS";
        String INCORRECT_PASSWORD = "INCORRECT_PASSWORD";
        String ADD_USER_FAILED = "ADD_USER_FAILED";
        String USER_IS_CLOSED = "USER_IS_CLOSED";
        String PLEASE_CHANGED_PASSWORD = "PLEASE_CHANGED_PASSWORD";
        String SESSION_LOST_ERROR = "SESSION_LOST_ERROR";
        String LOGOIN_BY_OTHER = "LOGOIN_BY_OTHER";
    }

    public interface SysRoleResult {
        String SYSROLE_IS_NULL = "SYSROLE_IS_NULL";
        String SYSROLE_IS_NOT_FOUND = "SYSROLE_IS_NOT_FOUND";
        String SYSROLE_IS_EXISTS = "SYSROLE_IS_EXISTS";
        String SYSROLE_MIDDLE_IS_EXISTS = "SYSROLE_MIDDLE_IS_EXISTS";
        String DELETE_SYSROLE_IS_FAILD = "DELETE_SYSROLE_IS_FAILD";
        String SYSROLE_ORVER_BD_ROLE_QUERY = "SYSROLE_ORVER_BD_ROLE_QUERY";
    }


    public interface SysPermission {
        String PERMISSION_IS_NULL = "PERMISSION_IS_NULL";
        String PERMISSION_IS_NOT_NULL = "PERMISSION_IS_NOT_NULL";
        String PERMISSION_HAS_CHILDREN = "PERMISSION_HAS_CHILDREN";
        String PARENT_PERMISSION_IS_NULL = "PARENT_PERMISSION_IS_NULL";
        String PERMISSION_HAS_USED = "PERMISSION_HAS_USED";
    }

    public interface Article {
        String ARTICLE_IS_NOT_NULL = "ARTICLE_IS_NOT_NULL";
        String ARTICLE_IS_NULL = "ARTICLE_IS_NULL";
    }

    public interface Customer{
        String CUSTOMER_IS_NOT_NULL = "CUSTOMER_IS_NOT_NULL";
        String CUSTOMER_IS_NULL = "CUSTOMER_IS_NULL";
    }

    public interface Comment{
        String COMMENT_IS_NOT_NULL = "COMMENT_IS_NOT_NULL";
        String COMMENT_IS_NULL = "COMMENT_IS_NULL";
    }

    public interface Favorite{
        String FAVORITE_IS_NOT_NULL = "FAVORITE_IS_NOT_NULL";
        String FAVORITE_IS_NULL = "FAVORITE_IS_NULL";
    }

    public interface Upload{
        String PARAM_ERROR = "PARAM_ERROR";
    }

    public interface All{
        String ALL_IS_NOT_NULL = "ALL_IS_NOT_NULL";
        String ALL_IS_NULL = "ALL_IS_NULL";
    }

    public interface Goods{
        String GOODS_IS_NOT_NULL = "GOODS_IS_NOT_NULL";
        String GOODS_IS_NULL = "GOODS_IS_NULL";
        String GOODS_DETAIL_IS_NULL= "GOODS_DETAIL_IS_NULL";
        String GOODS_DETAIL_IS_NOT_NULL="GOODS_DETAIL_IS_NOT_NULL";
        String GOODS_RESOURCE_IS_NULL="GOODS_RESOURCE_IS_NULL";
        String GOODS_RESOURCE_IS_NOT_NULL="GOODS_RESOURCE_IS_NOT_NULL";
        String GOODS_DETAIL_IS_LOCKED = "GOODS_DETAIL_IS_LOCKED";
        String GOODS_DETAIL_UN_LOCK_FAILED = "GOODS_DETAIL_UN_LOCK_FAILED";
    }

    public interface Order{
        String GOODS_DETAIL_NOT_ENOUGH ="GOODS_DETAIL_NOT_ENOUGH";
        String GOODS_STOCK_NOT_ENOUGH = "GOODS_STOCK_NOT_ENOUGH";
        String ORDER_IS_LOCKED = "ORDER_IS_LOCKED";
        String ORDER_UN_LOCK_FAILED = "ORDER_UN_LOCK_FAILED";
        String SHOPPING_CART_IS_NULL = "SHOPPING_CART_IS_NULL";
        String ORDER_IS_NULL = "ORDER_IS_NULL";
        String SHOPPING_CART_EMPTY = "SHOPPING_CART_EMPTY";
        String PRE_PAY_FAILED = "PRE_PAY_FAILED";
        String ORDER_ALREADY_SEND = "ORDER_ALREADY_SEND";
        String ORDER_STATUS_INVALID = "ORDER_STATUS_INVALID";
        String REFUND_FAILED = "REFUND_FAILED";
        String ALREADY_REFUND = "ALREADY_REFUND";
        String ORDER_IS_ALREADY_PAY_FOR_BACK = "ORDER_IS_ALREADY_PAY_FOR_BACK";
        String GOODS_ALREADY_IN_SHOPPING_CART = "GOODS_ALREADY_IN_SHOPPING_CART";
        String ORDER_ALREADY_PAY = "ORDER_ALREADY_PAY";
    }

    public interface CustomerAddress{
        String ADDRESS_IS_NULL = "ADDRESS_IS_NULL";
    }

    public interface Notification{
        String NOTIFICATION_IS_NULL = "NOTIFICATION_IS_NULL";
        String NOT_ALL_NOTIFICATION = "NOT_ALL_NOTIFICATION";
    }

    public interface Suggest{
        String SUGGEST_IS_NULL = "SUGGEST_IS_NULL";
    }

}

