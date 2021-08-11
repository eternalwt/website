package com.greengiant.infrastructure.es;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

//@Configuration
public class ESConfig {

    @Value("${spring.elasticsearch.rest.uris}")
    private String ADDR;

    // todo 找到 RestHighLevelClient 的标准配置方式，把地址配到配置文件中

    @Bean
    public RestHighLevelClient client() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(ADDR)
                .build();
        return RestClients.create(clientConfiguration).rest();
    }

}
