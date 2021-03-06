package com.mauersu.demo.mybatis.mapper;

import com.mauersu.demo.mybatis.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper  {

    void updateUsernameById(@Param("id") String id,@Param("name") String name);

    int updatePassword(User user);

    User selectById(String id);
}
