package com.mauersu.demo.oauth2.server.core.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OauthClientDetails implements Serializable {

    private static final long serialVersionUID = 421783821058285802L;

    private String clientId;

    private String resourceIds;

    private String clientSecret;

    private String scope;

    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private Byte autoapprove;

    private String originSecret;

    private Long createUser;

    private Date createTime;

    private Long updateUser;

    private Date updateTime;

    private Long isDelete;

}
