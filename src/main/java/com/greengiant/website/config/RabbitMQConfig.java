package com.greengiant.website.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue helloQueue() {
        Queue queue = new Queue("hello");
        return queue;
    }

}
