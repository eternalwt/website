package com.greengiant.infrastructure.messaging;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Producer {

    // AmqpTemplate是一个接口，通过调试这里最终注入的是RabbitTemplate：https://stackoverflow.com/questions/45875167/which-one-to-use-rabbittemplate-or-amqptemplate
    // 【但是RabbitmqTemplate的方法更多一些，包括setConfirmCallback、setReturnCallback】：https://www.cnblogs.com/timseng/p/11688019.html
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpAdmin admin;


    public void send(Object message){
        rabbitTemplate.convertAndSend("HEADER_NAME", null, message);
    }

    public void send(Message message){
        rabbitTemplate.convertAndSend("HEADER_NAME", null, message);
    }

    /**
     * @param routingKey 路由关键字
     * @param msg 消息体
     */
    public void sendDirectMsg(String routingKey, String msg) {
        rabbitTemplate.convertAndSend(routingKey, msg);
    }

    /**
     * @param routingKey 路由关键字
     * @param msg 消息体
     * @param exchange 交换机
     */
    public void sendExchangeMsg(String exchange, String routingKey, String msg) {
        rabbitTemplate.convertAndSend(exchange, routingKey, msg);
    }

    /**
     * @param map 消息headers属性
     * @param exchange 交换机
     * @param msg 消息体
     */
    public void sendHeadersMsg(String exchange, String msg, Map<String, Object> map) {
        rabbitTemplate.convertAndSend(exchange, null, msg, message -> {
            message.getMessageProperties().getHeaders().putAll(map);
            return message;
        });
    }

}
