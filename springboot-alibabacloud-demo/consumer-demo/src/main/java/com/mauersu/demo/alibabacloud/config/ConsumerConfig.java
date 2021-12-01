package com.mauersu.demo.alibabacloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConsumerConfig {

    /**
     * @description 注入RestTemplate实例
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate1() {
        return new RestTemplate();
    }
}
