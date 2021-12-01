package com.mauersu.demo.oauth2.server.mapper;


import com.mauersu.demo.oauth2.server.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

public interface UserMapper {

    void updateUsernameById(@Param("id") String id,@Param("name") String name);

    User selectById(String id);

    User selectByUsername(String username);
}
