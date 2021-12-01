package com.mauersu.demo.oauth2.server.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Role implements Serializable {

    private Long id;

    private String name;
}
