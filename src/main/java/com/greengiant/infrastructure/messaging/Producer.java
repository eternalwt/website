package com.greengiant.infrastructure.messaging;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Producer {
    // https://blog.csdn.net/cs_hnu_scw/article/details/81040834

    @Autowired
    private AmqpTemplate amqpTemplate;// todo 有哪些方法、各有什么作用？

    @Autowired
    private AmqpAdmin admin;// todo 有哪些方法、各有什么作用？


    public void send(Object message){
        amqpTemplate.convertAndSend("HEADER_NAME", null, message);
    }

    public void send(Message message){
        amqpTemplate.convertAndSend("HEADER_NAME", null, message);
    }

    /**
     * @param routingKey 路由关键字
     * @param msg 消息体
     */
    public void sendDirectMsg(String routingKey, String msg) {
        amqpTemplate.convertAndSend(routingKey, msg);
    }

    /**
     * @param routingKey 路由关键字
     * @param msg 消息体
     * @param exchange 交换机
     */
    public void sendExchangeMsg(String exchange, String routingKey, String msg) {
        amqpTemplate.convertAndSend(exchange, routingKey, msg);
    }

    /**
     * @param map 消息headers属性
     * @param exchange 交换机
     * @param msg 消息体
     */
    public void sendHeadersMsg(String exchange, String msg, Map<String, Object> map) {
        amqpTemplate.convertAndSend(exchange, null, msg, message -> {
            message.getMessageProperties().getHeaders().putAll(map);
            return message;
        });
    }

}
