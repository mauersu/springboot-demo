package com.mauersu.demo.statemachine.controller;


import com.alibaba.fastjson.JSON;
import com.mauersu.demo.statemachine.config.OrderEvents;
import com.mauersu.demo.statemachine.config.OrderStateMachineBuilder;
import com.mauersu.demo.statemachine.config.OrderStates;
import com.mauersu.demo.statemachine.entity.Order;
import com.mauersu.demo.statemachine.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class OrderControler {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    private BeanFactory beanFactory;
    @Autowired
    private OrderStateMachineBuilder orderStateMachineBuilder;
    @Resource(name="orderPersister")
    private StateMachinePersister<OrderStates, OrderEvents, Order> persister;


    //http://localhost:8202/payOrder
    //数据库 初始状态为0  0->1
    @GetMapping("/payOrder")
    public Order payOrder() throws Exception {
        StateMachine<OrderStates, OrderEvents> stateMachine = orderStateMachineBuilder.build(beanFactory);
        //查询订单
//        Order order = new Order();
//        order.setId(123L);
//        order.setStatus(OrderStates.WAITING_FOR_RECEIVE.getCode());
        Order order = orderMapper.findOrderById(123l);
        //恢复状态机状态
        persister.restore(stateMachine, order);
        //查看恢复后状态机的状态
        log.info("恢复后的状态：" + stateMachine.getState().getId());

        // 触发RECEIVE事件 返回true代表状态转换成功 false则表示转换状态失败
        boolean flag = stateMachine.sendEvent(OrderEvents.PAY);
        log.info("触发RECEIVE事件：flag" +flag);
        if (!flag) {
            log.error("支付失败,请检查order当前状态,order：" + JSON.toJSONString(order));
            throw new RuntimeException("支付失败");
        }
        log.info("触发RECEIVE事件：" + stateMachine.getState().getId());

        order.setStatus(stateMachine.getState().getId().getCode());
        int ret = orderMapper.updateOrderStatusById(order);
        return order;
    }

    //http://localhost:8202/receiveOrder
    //数据库 1->2
    @GetMapping("/receiveOrder")
    public Order receiveOrder() throws Exception {
        StateMachine<OrderStates, OrderEvents> stateMachine = orderStateMachineBuilder.build(beanFactory);
        //查询订单
//        Order order = new Order();
//        order.setId(123L);
//        order.setStatus(OrderStates.WAITING_FOR_RECEIVE.getCode());
        Order order = orderMapper.findOrderById(123l);
        //恢复状态机状态
        persister.restore(stateMachine, order);
        //查看恢复后状态机的状态
        log.info("恢复后的状态：" + stateMachine.getState().getId());

        // 触发RECEIVE事件  返回true代表状态转换成功 false则表示转换状态失败
        boolean flag = stateMachine.sendEvent(OrderEvents.RECEIVE);
        log.info("触发RECEIVE事件：flag" +flag);
        if (!flag) {
            log.error("收货失败,请检查order当前状态,order：" + JSON.toJSONString(order));
            throw new RuntimeException("收货失败");
        }
        log.info("触发RECEIVE事件：" + stateMachine.getState().getId());

        order.setStatus(stateMachine.getState().getId().getCode());
        int ret = orderMapper.updateOrderStatusById(order);
        return order;
    }



    //http://localhost:8202/backOrder
    //数据库 2->0
    @GetMapping("/backOrder")
    public Order backOrder() throws Exception {
        StateMachine<OrderStates, OrderEvents> stateMachine = orderStateMachineBuilder.build(beanFactory);
        //查询订单
//        Order order = new Order();
//        order.setId(123L);
//        order.setStatus(OrderStates.WAITING_FOR_RECEIVE.getCode());
        Order order = orderMapper.findOrderById(123l);
        //恢复状态机状态
        persister.restore(stateMachine, order);
        //查看恢复后状态机的状态
        log.info("恢复后的状态：" + stateMachine.getState().getId());

        // 触发RECEIVE事件  返回true代表状态转换成功 false则表示转换状态失败
        boolean flag = stateMachine.sendEvent(OrderEvents.BACK);
        log.info("触发 BACK 事件：flag" +flag);
        if (!flag) {
            log.error("退货退款失败,请检查order当前状态,order：" + JSON.toJSONString(order));
            throw new RuntimeException("退货退款失败");
        }
        log.info("触发 BACK 事件：" + stateMachine.getState().getId());

        order.setStatus(stateMachine.getState().getId().getCode());
        int ret = orderMapper.updateOrderStatusById(order);
        return order;
    }

}
