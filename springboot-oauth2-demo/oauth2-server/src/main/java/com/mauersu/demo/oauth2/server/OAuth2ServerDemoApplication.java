package com.mauersu.demo.oauth2.server;

import com.mauersu.demo.oauth2.common.annotation.EnableDemoResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableDemoResourceServer
@ComponentScan(value = {"com.mauersu.demo.oauth2.server"})
@MapperScan("com.mauersu.demo.oauth2.server.mapper")
public class OAuth2ServerDemoApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(OAuth2ServerDemoApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
