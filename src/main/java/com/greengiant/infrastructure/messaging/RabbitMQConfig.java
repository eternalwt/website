package com.greengiant.infrastructure.messaging;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Configuration
@Order(100)
public class RabbitMQConfig {

    // todo 项目中正确的打开方式应该是：1.在PostConstruct里面统一初始化交换机、队列、绑定关系；2.设计合理的配置项定义交换机、队列、绑定关系【如果绑定关系不好设计，可以先在代码里面写，先满足功能】
//    @PostConstruct

    // ------------------------------------------------------------------------

    private static final String topicExchangeName = "topic-exchange";
    private static final String fanoutExchange = "fanout-exchange";
    private static final String headersExchange = "headers-exchange";

    @Bean
    public void init() {
        // todo 用返回void的bean的方式初始化交换机、队列、绑定关系
    }

    /**
     * 声明队列
     * @return
     */
    @Bean
    public Queue helloQueue() {
        return new Queue("cord", false, true, true);
//        Queue queue = new Queue("hello");
//        return queue;
    }

    /**
     * 声明Topic交换机
     * @return
     */
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(topicExchangeName);
    }

    /**
     * 将队列与Topic交换机进行绑定，并指定路由键
     * @param queue
     * @param topicExchange
     * @return
     */
    @Bean
    Binding topicBinding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("org.cord.#");
    }

    /**
     * 声明fanout交换机
     * @return
     */
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(fanoutExchange);
    }

    /**
     * 将队列与fanout交换机进行绑定
     * @param queue
     * @param fanoutExchange
     * @return
     */
    @Bean
    Binding fanoutBinding(Queue queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    /**
     * 声明Headers交换机
     * @return
     */
    @Bean
    HeadersExchange headersExchange() {
        return new HeadersExchange(headersExchange);
    }

    /**
     * 将队列与headers交换机进行绑定
     * @param queue
     * @param headersExchange
     * @return
     */
    @Bean
    Binding headersBinding(Queue queue, HeadersExchange headersExchange) {
        Map<String, Object> map = new HashMap<>();
//        map.put("First","A");
//        map.put("Fourth","D");

        //whereAny表示部分匹配，whereAll表示全部匹配
//        return BindingBuilder.bind(queue).to(headersExchange).whereAll(map).match();
        return BindingBuilder.bind(queue).to(headersExchange).whereAny(map).match();
    }

}
