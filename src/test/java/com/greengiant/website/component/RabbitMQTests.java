package com.greengiant.website.component;

import com.greengiant.infrastructure.messaging.Producer;
import com.greengiant.website.WebsiteApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {WebsiteApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RabbitMQTests {

    @Autowired
    private Producer producer;

    // Direct
    @Test
    public void sendDirectMsg() {// todo 我的DirectExchange的设置有问题，导致消费完了队列就不在了
        producer.sendDirectMsg("cord", String.valueOf(System.currentTimeMillis()));
        producer.sendDirectMsg("cord", String.valueOf(System.currentTimeMillis()));
        producer.sendDirectMsg("cord", String.valueOf(System.currentTimeMillis()));
        producer.sendDirectMsg("cord", String.valueOf(System.currentTimeMillis()));
        producer.sendDirectMsg("cord", String.valueOf(System.currentTimeMillis()));
    }

    // Topic
    @Test
    public void sendTopicMsg() {
        producer.sendExchangeMsg("topic-exchange","org.cord.test", "hello world1");
        producer.sendExchangeMsg("topic-exchange","org.cord.test", "hello world2");
        producer.sendExchangeMsg("topic-exchange","org.cord.test", "hello world3");
        producer.sendExchangeMsg("topic-exchange","org.cord.test", "hello world4");
        producer.sendExchangeMsg("topic-exchange","org.cord.test", "hello world5");
    }

    // Fanout
    @Test
    public void sendFanoutMsg() {
        producer.sendExchangeMsg("fanout-exchange", "abcdefg", String.valueOf(System.currentTimeMillis()));
    }

    // Headers
    @Test
    public void sendHeadersMsg() {
        Map<String, Object> map = new HashMap<>();
        map.put("First","A");
        producer.sendHeadersMsg("headers-exchange", "hello word", map);
    }

}
