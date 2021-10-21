package com.greengiant.infrastructure.messaging;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * RabbitMQ配置类
 */
@Configuration
@Order(100)
public class RabbitMQConfig {

    private static final String topicExchangeName = "topic-exchange";
    private static final String fanoutExchange = "fanout-exchange";
    private static final String headersExchange = "headers-exchange";

    @Bean
//    @DependsOn({"producer"})
    public void init() {
        // todo 用返回void的bean的方式初始化交换机、队列、绑定关系
        // todo 综合处理好配置、消息类型、交换机类型及绑定关系、listener，做到容易配置和使用
    }

    /**
     * 因为这个listener container 是自己创建的，会抑制spring boot自动配置
     ，所以这些配置就不生效了。spring.rabbitmq.listener.*
     *
     * @param connectionFactory
     * @return
     */
//    @Bean
////    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
////        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
////        factory.setConnectionFactory(connectionFactory);
//////        factory.setMessageConverter(jsonConverter());
//////        factory.setErrorHandler(errorHandler());
////        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
////        return factory;
////    }

//    @Bean
//    public DirectRabbitListenerContainerFactory directRabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
//        DirectRabbitListenerContainerFactory factory = new DirectRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
////        factory.setMessageConverter(jsonConverter());
////        factory.setErrorHandler(errorHandler());
//        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        return factory;
//    }

    /**
     * 声明队列
     * @return
     */
    @Bean
    public Queue helloQueue() {
//        return new Queue("cord", true, true, false);
        Queue queue = new Queue("hello");
//        Queue que = new Queue("cord");// todo 后来加的
        return queue;
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
        map.put("First","A");
        map.put("Fourth","D");

        //whereAny表示部分匹配，whereAll表示全部匹配
//        return BindingBuilder.bind(queue).to(headersExchange).whereAll(map).match();
        return BindingBuilder.bind(queue).to(headersExchange).whereAny(map).match();
    }

//    /**
//     * 定义消息转换实例 ，转化成 JSON传输
//     *
//     * @return Jackson2JsonMessageConverter
//     */
//    @Bean
//    public MessageConverter integrationEventMessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
//
//    /**
//     * 配置启用rabbitmq事务
//     *
//     * @param connectionFactory connectionFactory
//     * @return RabbitTransactionManager
//     */
//    @Bean
//    public RabbitTransactionManager rabbitTransactionManager(CachingConnectionFactory connectionFactory) {
//        return new RabbitTransactionManager(connectionFactory);
//    }

}
