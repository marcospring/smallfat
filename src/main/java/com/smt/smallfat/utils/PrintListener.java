package com.smt.smallfat.utils;

import com.csyy.core.datasource.impl.cache.DefaultCacheReadAndWriteDataSessionFactory;
import com.csyy.core.listener.BaseSpringContextRefreshListener;
import com.csyy.redis.api.RedisSession;
import com.csyy.redis.utils.mq.MsgHandler;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;

public class PrintListener extends BaseSpringContextRefreshListener {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void doListen(ContextRefreshedEvent contextRefreshedEvent) {
//        logger.info("添加过期订阅====>   go");
//        OrderService orderService = contextRefreshedEvent.getApplicationContext().getBean("orderServiceImpl",
//                OrderService.class);
//        RedisSession redisSession = contextRefreshedEvent.getApplicationContext().getBean
//                ("readAndWriteDataSessionFactory", DefaultCacheReadAndWriteDataSessionFactory.class)
//                .getRedisSessionFactory().getSession();
//        try {
//            redisSession.subscribe(new MsgHandler(message -> orderService.orderOverdueCallBack(message.getMsg())), Constant
//                    .REDIS_KEY_OVERDUE_CHANEL);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
        System.out.println("      _                __                                             _    ");
        System.out.println("     | |              / _|                                           | |   ");
        System.out.println("  ___| | __  ______  | |_ _ __ __ _ _ __ ___   _____      _____  _ __| | __");
        System.out.println(" |_  / |/ / |______| |  _| '__/ _` | '_ ` _ \\ / _ \\ \\ /\\ / / _ \\| '__| |/ /");
        System.out.println("  / /|   <           | | | | | (_| | | | | | |  __/\\ V  V / (_) | |  |   <");
        System.out.println(" /___|_|\\_\\          |_| |_|  \\__,_|_| |_| |_|\\___| \\_/\\_/ \\___/|_|  |_|\\_\\");
        System.out.println("");
        System.out.println("");
    }
}
