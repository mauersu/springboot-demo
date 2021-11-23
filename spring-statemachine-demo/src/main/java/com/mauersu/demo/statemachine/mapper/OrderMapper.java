package com.mauersu.demo.statemachine.mapper;

import com.mauersu.demo.statemachine.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper//指定这是一个操作数据库的mapper
public interface OrderMapper {

    int addOrder(Order order);

    int updateOrderStatusById(Order order);

    Order findOrderById(Long id);
}
