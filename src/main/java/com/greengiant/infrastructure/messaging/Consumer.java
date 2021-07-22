package com.greengiant.infrastructure.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    // todo 思考我的kafka代码能不能这样封装

    @RabbitListener(queues = "cord")// todo 监听多个队列。公司的代码好像
    //@RabbitListener(queues = "cord", containerFactory="myFactory")
    public void processMessage(String msg) {
        System.out.format("Receiving Message: -----[%s]----- \n.", msg);
    }

}
