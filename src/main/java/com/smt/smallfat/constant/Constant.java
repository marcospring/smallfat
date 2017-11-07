package com.smt.smallfat.constant;


import java.util.Properties;

public class Constant extends com.csyy.constant.Constants {


    public static final String PAGE_NO = "pageNo";
    public static String DEFULT_PASSWORD = "123456Ab";//系统默认密码
    public static final String PAGE_SIZE = "pageSize";
    public static final String DEFAULT_PASSWORD = "123qwe";
    //参数的key名称
    public static final String PARAM_KEY = "param";
    //上传失败code
    public static final String FILE_UPLOAD_FAIL = "500";
    //上传成功code
    public static final String FILE_UPLOAD_SUCCESS = "200";

    //缩略图宽
    public static final Integer IMG_THUMBNAIL_WIDTH = 100;
    //缩略图高
    public static final Integer IMG_THUMBNAIL_HEIGHT = 80;
    //文件存储路径
    public static final String FILE_PATH = "/Users/zhangkui/MyUpload";
    //app回传用户ID
    public static final String USER_ID = "userId";
    //数据分隔符
    public static final String DATA_SPECTOR = "\\|";
    /**
     * app精选文章
     */
    public static final int APP_ARTICLE = 1;
    /**
     * 非app精选文章
     */
    public static final int NOT_APP_ARTICLE = 0;

    public static final String PUSH_TYPE="pushType";

    public static final String REFUND_BUFF = "refund_";

    public static final String REDIS_KEY_OVERDUE_CHANEL = "__keyevent@0__:expired";

    public static final String ARTICLE_SHARE_URL = "http://share.test.huaxi0.com/activity/share/article.html?id=";

    public static final String GOODS_SHARE_URL = "http://share.test.huaxi0.com/activity/share/goods.html?id=";

    public static final String ALL_SHARE_URL = "http://share.test.huaxi0.com/activity/share/wiki.html?id=";

    public static final String ORDER_PREFIX = "ORDER_";

    /**
     * 通用的常量
     */
    public interface CommonConstant {

        //启用状态
        int STATUS_ENABLE = 0;
        //禁用状态
        int STATUS_DISABLE = 1;
        //默认每页数量
        int DEFAULT_PAGE_COUNT = 20;

    }

    public interface status{
        public static final int open = 0;
        public static final int close = 1;
    }

    public interface OrderStatus {
        /**
         * 待付款
         */
        int ORDER_FOR_PAY = 1;
        /**
         * 待发货
         */
        int ALREADY_PAY = 2;
        /**
         * 已发货
         */
        int ALREADY_SEND = 3;
        /**
         * 退款审核中
         */
        int PAY_FOR_BACK = 4;
        /**
         * 退款成功
         */
        int ALREADY_PAY_FOR_BACK = 5;
        /**
         * 交易关闭
         */
        int ORDER_CLOSED = 6;
        /**
         * 订单过期
         */
        int ORDER_DELAY = 7;
    }

    public interface PayParam{
         String NONCE_STR = "nonceStr";
        String BODY = "body";
        String OUT_TRADE_NO = "outTradeNo";
        String TOTAL_FEE = "totalFee";
        String SPBILL_CREATE_IP = "spbillCreateIp";
    }

    public interface PushType{
        String ARTICLE = "0";
        String GOODS = "1";
        String DELIVER_GOODS = "2";
        String SYSTEM_NOTICE = "3";
        String COMMENT ="4";
    }

    public interface  Lock{
        String ORDER_LOCK = "ORDER_LOCK";
        String DETAIL_LOCK = "DETAIL_LOCK";
        String REFUND_LOCK ="REFUND_LOCK";
    }
}
