package com.mauersu.demo.oauth2.resource;

import com.mauersu.demo.oauth2.common.annotation.EnableDemoResourceServer;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author
 */
@SpringBootApplication
@EnableDemoResourceServer
@EnableFeignClients(value = {"com.mauersu.demo.oauth2.resource.feign"})
public class DemoAuthResourceDemoApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoAuthResourceDemoApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
