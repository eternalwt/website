package com.greengiant.website.component;

import com.greengiant.infrastructure.messaging.Producer;
import com.greengiant.website.WebsiteApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebsiteApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RabbitMQTests {

    // todo 把Channel那些测一下

    // todo 1.我用的时候怎么用？2.如何监听多个queue？（例如公司里面那种代码）3.怎么灵活绑定、怎么搞多个topic、queue的应用？

    @Autowired
    private Producer producer;

    // Direct
    @Test
    public void sendDirectMsg() {
        producer.sendDirectMsg("cord", String.valueOf(System.currentTimeMillis()));
    }

    // Topic
    @Test
    public void sendTopicMsg() {
        producer.sendExchangeMsg("topic-exchange","org.cord.test", "hello world");
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
