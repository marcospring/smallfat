package com.smt.smallfat.constant;

public class Constant extends com.csyy.constant.Constants {


    public static final String PAGE_NO = "pageNo";
    public static final String PAGE_SIZE = "pageSize";
    public static String DEFULT_PASSWORD = "123456Ab";//系统默认密码
    /**
     * 通用的常量
     */
    public interface CommonConstant{

        //启用状态
        int STATUS_ENABLE = 0;
        //禁用状态
        int STATUS_DISABLE = 1;
        //默认每页数量
        int DEFAULT_PAGE_COUNT = 20;
    }
}
