package com.mauersu.demo.oauth2.server.core.configure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JWTTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> info = new HashMap<>();
        org.springframework.security.core.userdetails.User authUser =  (org.springframework.security.core.userdetails.User)oAuth2Authentication.getUserAuthentication().getPrincipal();

        info.put("username", authUser.getUsername());
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(info);
        return oAuth2AccessToken;
    }
}
