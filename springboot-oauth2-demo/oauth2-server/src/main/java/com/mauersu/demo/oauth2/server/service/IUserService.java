package com.mauersu.demo.oauth2.server.service;

import com.mauersu.demo.oauth2.server.entity.User;

public interface IUserService {
    User getUserByUsername(String username);

    String concatAuthoritiesByUserId(Long id);
}
