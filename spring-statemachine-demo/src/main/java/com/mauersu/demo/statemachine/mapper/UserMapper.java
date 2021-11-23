package com.mauersu.demo.statemachine.mapper;

import com.mauersu.demo.statemachine.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper  {

    void updateUsernameById(@Param("id") String id,@Param("name") String name);

    int updatePassword(User user);

    User selectById(String id);
}
