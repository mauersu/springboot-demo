package com.mauersu.demo.oauth2.server.service.impl;


import com.mauersu.demo.oauth2.server.service.IUserService;
import com.mauersu.demo.oauth2.server.util.SpringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import com.mauersu.demo.oauth2.server.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
public class ItjcloudUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Assert.hasLength(username, "username is empty");
        HttpServletRequest httpServletRequest = SpringUtils.getHttpServletRequest();
        User dbUser = userService.getUserByUsername(username);

        if (Objects.isNull(dbUser)) {
            throw new UsernameNotFoundException("cannot find user by username:"+username);
        }

        String authorities = userService.concatAuthoritiesByUserId(dbUser.getId());
        String password = dbUser.getPassword();
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.NO_AUTHORITIES;
        if (StringUtils.isNotBlank(authorities)) {
            grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
        }
        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(dbUser.getUsername(), password, true, true, true, true,
                grantedAuthorities);

        return userDetails;
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        log.info("encode:"+ encoder.encode("111111"));
    }

}
