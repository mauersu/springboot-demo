package com.mauersu.demo.oauth2.server.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Resource implements Serializable {

    private Long id;

    private String name;

    private String authority;


}
