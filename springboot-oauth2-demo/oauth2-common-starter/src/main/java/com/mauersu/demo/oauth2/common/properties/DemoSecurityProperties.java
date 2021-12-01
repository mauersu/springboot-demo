package com.mauersu.demo.oauth2.common.properties;

import com.mauersu.demo.oauth2.common.constant.EndpointConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = DemoSecurityProperties.PREFIX)
@Data
public class DemoSecurityProperties {

    public static final String PREFIX = "demo.cloud.security";
    /**
     * JWT加签密钥
     */
    private String jwtAccessKey;
    /**
     * 是否使用 JWT令牌
     */
    private Boolean enableJwt;
    /**
     * 是否开启安全配置
     */
    private Boolean enable;
    /**
     * 配置需要认证的uri，默认为所有/**
     */
    private String authUri = EndpointConstant.ALL;
    /**
     * 免认证资源路径，支持通配符
     * 多个值时使用逗号分隔
     */
    private String anonUris;

}
