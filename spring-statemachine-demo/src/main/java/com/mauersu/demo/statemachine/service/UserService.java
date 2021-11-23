package com.mauersu.demo.statemachine.service;

import com.mauersu.demo.statemachine.entity.User;
import com.mauersu.demo.statemachine.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public void updateUsernameById(String id, String name) {
        userMapper.updateUsernameById(id,name);
    }

    public int updatePassword(User user) {
        return userMapper.updatePassword(user);
    }
}
