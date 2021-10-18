package com.greengiant.infrastructure.messaging;

import org.springframework.amqp.core.*;
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
     * 因为这个listener container 是自己创建的，会抑制spring boot自动配置
     ，所以这些配置就不生效了。spring.rabbitmq.listener.*
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(jsonConverter());
//        factory.setErrorHandler(errorHandler());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    @Bean
    public RabbitListenerContainerFactory directRabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(jsonConverter());
//        factory.setErrorHandler(errorHandler());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    /**
     * 声明队列
     * @return
     */
    @Bean
    public Queue helloQueue() {
        // todo 我测手动ack没成功是不是因为这里的配置不对？再看durable和autoDelete参数
        //  1.在producer函数里发送多条消息测试；
        //  2.修改这里的参数测试
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
