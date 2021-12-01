package com.mauersu.demo.oauth2.common.configure;

import com.mauersu.demo.oauth2.common.constant.EndpointConstant;
import com.mauersu.demo.oauth2.common.handler.DemoAccessDeniedHandler;
import com.mauersu.demo.oauth2.common.handler.DemoAuthExceptionEntryPoint;
import com.mauersu.demo.oauth2.common.properties.DemoSecurityProperties;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 其主要用于资源的保护，客户端只能通过OAuth2协议发放的令牌来从资源服务器中获取受保护的资源。
 */
//用于开启资源服务器相关配置
@EnableResourceServer
@EnableAutoConfiguration
public class DemoResourceServerConfigure extends ResourceServerConfigurerAdapter {

    private DemoSecurityProperties properties;
    private DemoAccessDeniedHandler accessDeniedHandler;
    private DemoAuthExceptionEntryPoint exceptionEntryPoint;

    @Autowired(required = false)
    public void setProperties(DemoSecurityProperties properties) {
        this.properties = properties;
    }

    @Autowired(required = false)
    public void setAccessDeniedHandler(DemoAccessDeniedHandler accessDeniedHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Autowired(required = false)
    public void setExceptionEntryPoint(DemoAuthExceptionEntryPoint exceptionEntryPoint) {
        this.exceptionEntryPoint = exceptionEntryPoint;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        if (properties == null) {
            permitAll(http);
            return;
        }
        String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(properties.getAnonUris(), ",");
        if (ArrayUtils.isEmpty(anonUrls)) {
            anonUrls = new String[]{};
        }
        if (ArrayUtils.contains(anonUrls, EndpointConstant.ALL)) {
            permitAll(http);
            return;
        }
        http.csrf().disable()
                //表明该安全配置对所有请求都生效
                .requestMatchers().antMatchers(properties.getAuthUri())
                .and()
                .authorizeRequests()
                .antMatchers(anonUrls).permitAll()
                .antMatchers("/**").permitAll()
                .antMatchers(properties.getAuthUri()).authenticated()
                .and()
                .httpBasic();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        if (exceptionEntryPoint != null) {
            resources.authenticationEntryPoint(exceptionEntryPoint);
        }
        if (accessDeniedHandler != null) {
            resources.accessDeniedHandler(accessDeniedHandler);
        }
    }

    private void permitAll(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().anyRequest().permitAll();
    }
}
