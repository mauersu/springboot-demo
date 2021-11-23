package com.mauersu.demo.statemachine.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Order implements Serializable {

    private long id;

    private String name;

    private Integer status;

}
