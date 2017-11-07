package com.smt.smallfat.utils;

import com.csyy.common.DateUtils;
import com.csyy.common.ExceptionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

/**
 * 订单  成
 * Created by zhangkui on 16/5/31.
 */
public class OrderNoCreator {
    private static Logger logger = LoggerFactory.getLogger(OrderNoCreator.class);

    private static final String machine = "01";

    //获取毫秒内唯一号码
    public static String createOrderNo() {
        String dateFormat = DateUtils.formatDate(new Date(), "yyMMdd");
        String timeFormat = DateUtils.formatDate(new Date(), "HHmmss");
        StringBuilder builder = new StringBuilder(machine);
        builder.append(dateFormat).append(timeFormat).append(IDCreator.create());
        return builder.toString();
    }

    public static String createOrderNo(String buff) {
        String dateFormat = DateUtils.formatDate(new Date(), "yyMMdd");
        String timeFormat = DateUtils.formatDate(new Date(), "HHmmss");
        StringBuilder builder = new StringBuilder(buff);
        builder.append(machine).append(dateFormat).append(timeFormat).append(IDCreator.create());
        return builder.toString();
    }
}

class IDCreator {
    private static Long second = 0L;
    private static Integer seed = 0;

    private synchronized static String getId() {
        if (second == 0)
            second = System.currentTimeMillis();
        if (second != System.currentTimeMillis()) {
            second = System.currentTimeMillis();
            seed = 0;
            return second.toString() + seed;
        } else {
            return second.toString() + ++seed;
        }
    }

    public static String create() {
        String id = getId();
        return id.substring(id.length() - 4, id.length());
    }
}