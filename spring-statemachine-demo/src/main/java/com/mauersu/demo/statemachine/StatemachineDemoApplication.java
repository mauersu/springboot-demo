package com.mauersu.demo.statemachine;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.mauersu.demo.statemachine.mapper")
public class StatemachineDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatemachineDemoApplication.class, args);
    }

}
