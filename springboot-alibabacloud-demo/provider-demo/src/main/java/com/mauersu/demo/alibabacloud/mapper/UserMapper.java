package com.mauersu.demo.alibabacloud.mapper;

import com.mauersu.demo.alibabacloud.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper  {

    void updateUsernameById(@Param("id") String id,@Param("name") String name);

    int updatePassword(User user);

    User selectById(String id);
}
