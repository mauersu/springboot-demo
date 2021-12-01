package com.mauersu.demo.oauth2.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


@Data
@SuppressWarnings("all")
@EqualsAndHashCode(callSuper = true)
public class AuthUser extends User {

    private static final long serialVersionUID = -6411066541689297219L;

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

    public AuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AuthUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
