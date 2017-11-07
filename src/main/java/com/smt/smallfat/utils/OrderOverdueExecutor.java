package com.smt.smallfat.utils;

import com.csyy.core.datasource.api.CacheReadAndWriteDataSessionFactory;
import com.csyy.redis.api.RedisSession;
import com.csyy.redis.utils.mq.Handler;
import com.csyy.redis.utils.mq.Message;
import com.csyy.redis.utils.mq.MsgHandler;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class OrderOverdueExecutor implements ServletContextListener {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private CacheReadAndWriteDataSessionFactory factory;
    private ApplicationContext applicationContext;
    private JedisPool pool;
    private OrderService orderService;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //获得spring容器
//        applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
//        if (applicationContext != null) {
//            pool = (JedisPool) applicationContext.getBean("jedisPool");
//            orderService = applicationContext.getBean("orderServiceImpl", OrderService.class);
//        } else {
//            logger.error("==============初始化容器失败");
//        }
//
//      //  RedisSession redisSession = factory.getRedisSessionFactory().getSession();
//        try {
//            pool.getResource().subscribe(new Message(),Constant.REDIS_KEY_OVERDUE_CHANEL);
////            redisSession.subscribe(new MsgHandler(new Handler() {
////                @Override
////                public void handle(Message message) {
////                    orderService.orderOverdueCallBack(message.getMsg());
////                }
////            }),Constant.REDIS_KEY_OVERDUE_CHANEL);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
