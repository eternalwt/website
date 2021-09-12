package com.greengiant.infrastructure.es;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

@Configuration
public class ESConfig {

    @Value("${spring.elasticsearch.rest.uris}")
    private String ADDR;

//    @Value("${spring.rabbitmq.username}")
//    private String username;
//
//    @Value("${spring.rabbitmq.password}")
//    private String password;
//
//    @Value("${spring.rabbitmq.port}")
//    private int port;
//
//    @Value("${spring.rabbitmq.host}")
//    private String host;

    @Value("${spring.rabbitmq.virtual-host:#{null}}")
    private String virtualHost;

    // todo 公司代码喜欢用ConnectionFactory，这样有什么好处？

    @Bean
    public RestHighLevelClient client() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(ADDR)
                .build();
        return RestClients.create(clientConfiguration).rest();
    }

}
