package com.mauersu.demo.oauth2.server.controller;

import com.mauersu.demo.oauth2.server.entity.User;
import com.mauersu.demo.oauth2.server.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @Autowired
    private UserMapper userMapper;

    @Value("${provider.name}")
    private String name;

    @GetMapping("/provider")
    public String provider() {
        return this.name;
    }

    //http://localhost:8081/test/1
    @RequestMapping("/test/{id}")
    public Object test(@PathVariable String id) {
        User user = userMapper.selectById(id);
        return user;
    }


}
