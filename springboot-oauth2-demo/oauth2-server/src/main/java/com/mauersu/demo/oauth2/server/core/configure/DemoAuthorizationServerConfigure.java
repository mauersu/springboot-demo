package com.mauersu.demo.oauth2.server.core.configure;

import com.mauersu.demo.oauth2.common.properties.DemoSecurityProperties;
import com.mauersu.demo.oauth2.server.core.service.impl.RedisAuthenticationCodeService;
import com.mauersu.demo.oauth2.server.core.service.impl.RedisClientDetailsService;
import com.mauersu.demo.oauth2.server.core.translator.DemoWebResponseExceptionTranslator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 认证服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class DemoAuthorizationServerConfigure extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailService;
    @Autowired
    private DemoWebResponseExceptionTranslator exceptionTranslator;
    @Autowired
    private DemoSecurityProperties properties;
    @Autowired
    private RedisAuthenticationCodeService authenticationCodeService;
    @Autowired
    private RedisClientDetailsService redisClientDetailsService;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(redisClientDetailsService);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {


        endpoints.tokenStore(tokenStore())
                .userDetailsService(userDetailService)
                .tokenEnhancer(enhancerChain())
                .authorizationCodeServices(authenticationCodeService)
                .authenticationManager(authenticationManager)
                .exceptionTranslator(exceptionTranslator);
        if (properties.getEnableJwt()) {
            endpoints.accessTokenConverter(jwtAccessTokenConverter());
        }
    }

    @Bean
    public TokenEnhancerChain enhancerChain() {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancers = new ArrayList<>();
        enhancers.add(tokenEnhancer());
        enhancers.add(jwtAccessTokenConverter());
        enhancerChain.setTokenEnhancers(enhancers);
        return enhancerChain;
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new JWTTokenEnhancer();
    }

    @Bean
    public TokenStore tokenStore() {
        if (properties.getEnableJwt()) {
            return new JwtTokenStore(jwtAccessTokenConverter());
        } else {
            RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
            // 解决每次生成的 token都一样的问题
            redisTokenStore.setAuthenticationKeyGenerator(oAuth2Authentication -> UUID.randomUUID().toString());
            return redisTokenStore;
        }
    }

    @Bean
    @Primary
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();

        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(redisClientDetailsService);
        return tokenServices;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        DefaultAccessTokenConverter defaultAccessTokenConverter = (DefaultAccessTokenConverter) accessTokenConverter.getAccessTokenConverter();
        DefaultUserAuthenticationConverter userAuthenticationConverter = new DefaultUserAuthenticationConverter();
        userAuthenticationConverter.setUserDetailsService(userDetailService);
        defaultAccessTokenConverter.setUserTokenConverter(userAuthenticationConverter);
        accessTokenConverter.setSigningKey(properties.getJwtAccessKey());
        return accessTokenConverter;
    }

    @Bean
    public ResourceOwnerPasswordTokenGranter resourceOwnerPasswordTokenGranter(AuthenticationManager authenticationManager, OAuth2RequestFactory oAuth2RequestFactory) {
        DefaultTokenServices defaultTokenServices = defaultTokenServices();
        if (properties.getEnableJwt()) {
            //defaultTokenServices.setTokenEnhancer(jwtAccessTokenConverter());
            defaultTokenServices.setTokenEnhancer(enhancerChain());
        }
        return new ResourceOwnerPasswordTokenGranter(authenticationManager, defaultTokenServices, redisClientDetailsService, oAuth2RequestFactory);
    }

    @Bean
    public DefaultOAuth2RequestFactory oAuth2RequestFactory() {
        return new DefaultOAuth2RequestFactory(redisClientDetailsService);
    }

}
