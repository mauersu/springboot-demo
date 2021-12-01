package com.mauersu.demo.alibabacloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.mauersu.demo.alibabacloud.*")
@MapperScan("com.mauersu.demo.alibabacloud.mapper")
@EnableDiscoveryClient
public class AlibabaCloudProviderDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlibabaCloudProviderDemoApplication.class, args);
    }

}
