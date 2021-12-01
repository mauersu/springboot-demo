package com.mauersu.demo.oauth2.common.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserEntity implements Serializable {

    private String username;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;


    private String userId;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 状态 0:正常
     */
    private Integer status;

    private String nickName;
}
