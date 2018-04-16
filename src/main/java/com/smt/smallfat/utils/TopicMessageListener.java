package com.smt.smallfat.utils;

import com.smt.smallfat.service.base.OrderService;
import com.smt.smallfat.service.house.CircleService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class TopicMessageListener implements MessageListener {

    private OrderService orderService;

    private CircleService circleService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        orderService.orderOverdueCallBack(new String(message.getBody()));
        circleService.imageOverdueCallBack(new String(message.getBody()));
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public CircleService getCircleService() {
        return circleService;
    }

    public void setCircleService(CircleService circleService) {
        this.circleService = circleService;
    }
}
