package com.mauersu.demo.oauth2.common.configure;

import com.mauersu.demo.oauth2.common.handler.DemoAccessDeniedHandler;
import com.mauersu.demo.oauth2.common.handler.DemoAuthExceptionEntryPoint;
import com.mauersu.demo.oauth2.common.properties.DemoSecurityProperties;
import com.mauersu.demo.oauth2.common.util.DemoSecurityUtil;
import feign.RequestInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.util.Base64Utils;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(DemoSecurityProperties.class)
@ConditionalOnProperty(value = "demo.cloud.security.enable", havingValue = "true", matchIfMissing = true)
public class DemoSecurityAutoConfigure extends GlobalMethodSecurityConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "accessDeniedHandler")
    public DemoAccessDeniedHandler accessDeniedHandler() {
        return new DemoAccessDeniedHandler();
    }

    @Bean
    @ConditionalOnMissingBean(name = "authenticationEntryPoint")
    public DemoAuthExceptionEntryPoint authenticationEntryPoint() {
        return new DemoAuthExceptionEntryPoint();
    }

    @Bean
    @ConditionalOnMissingBean(value = PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(DefaultTokenServices.class)
    public DemoUserInfoTokenServices demoUserInfoTokenServices(ResourceServerProperties properties) {
        return new DemoUserInfoTokenServices(properties.getUserInfoUri(), properties.getClientId());
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return new OAuth2MethodSecurityExpressionHandler();
    }

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor() {
        return requestTemplate -> {
            String authorizationToken = DemoSecurityUtil.getCurrentTokenValue();
            if (StringUtils.isNotBlank(authorizationToken)) {
                requestTemplate.header(HttpHeaders.AUTHORIZATION, DemoSecurityUtil.OAUTH2_TOKEN_TYPE + authorizationToken);
            }
        };
    }
}
