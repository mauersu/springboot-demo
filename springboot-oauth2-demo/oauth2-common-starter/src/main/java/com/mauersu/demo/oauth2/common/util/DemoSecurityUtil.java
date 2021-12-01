package com.mauersu.demo.oauth2.common.util;

import com.alibaba.fastjson.JSON;
import com.mauersu.demo.oauth2.common.entity.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class DemoSecurityUtil {

    /**
     * OAUTH2 令牌类型 https://oauth.net/2/bearer-tokens/
     */
    public static String OAUTH2_TOKEN_TYPE = "bearer";

    /**
     * 获取当前令牌内容 仅限resource端使用
     *
     * @return String 令牌内容
     */
    public static String getCurrentTokenValue() {
        try {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) getOauth2Authentication().getDetails();
            return details.getTokenValue();
        } catch (Exception ignore) {
            return null;
        }
    }

    /**
     * 获取在线用户信息 仅限resource端使用
     * @return
     */
    protected static OAuth2Authentication getOauth2Authentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (OAuth2Authentication) authentication;
    }

    /**
     * 获取在线用户信息 仅限resource端使用
     * @return
     */
    @SuppressWarnings("all")
    protected static LinkedHashMap<String, Object> getAuthenticationDetails() {
        return (LinkedHashMap<String, Object>) getOauth2Authentication().getUserAuthentication().getDetails();
    }

    /**
     * 获取在线用户信息 resource端与认证端都可使用
     * @return CurrentUser 当前用户信息
     */
    public static AuthUser getCurrentUser() {
        Object principal = getOauth2Authentication().getPrincipal();
        if (principal instanceof AuthUser) {
            return (AuthUser) principal;
        }
        return getItjcloudAuthUser();
    }

    /**
     * 获取在线用户信息 仅限resource端使用
     * @return
     */
    private static AuthUser getItjcloudAuthUser() {
        try {
            LinkedHashMap<String, Object> authenticationDetails = getAuthenticationDetails();
            LinkedHashMap<String, Object> principal = (LinkedHashMap<String, Object>)authenticationDetails.get("principal");
            UserEntity user = JSON.parseObject(JSON.toJSONString(principal), UserEntity.class);

            List<Map<String, String>> permissions = (List<Map<String, String>>)principal.get("authorities");

            List<GrantedAuthority> grantedAuthorities = new ArrayList<>(permissions.size());
            for (Map<String, String> authority : permissions) {
                grantedAuthorities.add(new SimpleGrantedAuthority(authority.get("authority")));
            }
            AuthUser authUser = new AuthUser(user.getUsername(), "", grantedAuthorities);

            BeanUtils.copyProperties(user, authUser);
            return authUser;
        } catch (Exception e) {
            log.error("获取当前用户信息失败", e);
            return null;
        }
    }

    /**
     * 获取当前用户名称 resource端与认证端都可使用
     *
     * @return String 用户名
     */
    public static String getCurrentUsername() {
        Object principal = getOauth2Authentication().getPrincipal();
        if (principal instanceof AuthUser) {
            return ((AuthUser) principal).getUsername();
        }

        return (String) getOauth2Authentication().getPrincipal();
    }

}
