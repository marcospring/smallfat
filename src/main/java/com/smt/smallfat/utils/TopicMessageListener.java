package com.smt.smallfat.utils;

import com.smt.smallfat.service.OrderService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class TopicMessageListener implements MessageListener {

    private OrderService orderService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        orderService.orderOverdueCallBack(new String(message.getBody()));
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
