package com.mauersu.demo.oauth2.server.service.impl;

import com.mauersu.demo.oauth2.server.entity.Resource;
import com.mauersu.demo.oauth2.server.entity.User;
import com.mauersu.demo.oauth2.server.mapper.ResourceMapper;
import com.mauersu.demo.oauth2.server.mapper.UserMapper;
import com.mauersu.demo.oauth2.server.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public String concatAuthoritiesByUserId(Long userId) {
        List<Resource> resources = resourceMapper.listResourceByUserId(userId);
        return resources.stream().map(Resource::getAuthority).collect(Collectors.joining(","));
    }
}
