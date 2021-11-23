package com.mauersu.demo.statemachine.controller;

import com.mauersu.demo.statemachine.entity.User;
import com.mauersu.demo.statemachine.mapper.UserMapper;
import com.mauersu.demo.statemachine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    //http://localhost:8202/test/1
    @RequestMapping("/test/{id}")
    public Object test(@PathVariable String id) {
        User user = userMapper.selectById(id);
        return user;
    }

    @RequestMapping("/test/{id}/{name}")
    public Object test3(@PathVariable String id, @PathVariable String name) {
        userService.updateUsernameById(id, name);
        return "success~";
    }

    @PostMapping("/test/upPassword")
    public Object test3(@RequestBody User user) {
        int i = userService.updatePassword(user);
        return i;
    }


}
