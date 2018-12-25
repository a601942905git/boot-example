package com.boot.example;

import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 * com.boot.example.OrderStateMachineEventConfig
 *
 * @author lipeng
 * @date 2018/12/25 下午5:25
 */
@WithStateMachine
public class OrderStateMachineEventConfig {

    /**
     * 支付事件
     */
    @OnTransition(source = "UNPAID", target = "WAITING_FOR_DELIVERY")
    public void pay() {
        System.out.println("=========================================");
        System.out.println("订单已支付,订单状态从未支付状态变成已支付状态");
        System.out.println("=========================================");
    }

    /**
     * 发货事件
     */
    @OnTransition(source = "WAITING_FOR_DELIVERY", target = "WAITING_FOR_RECEIVE")
    public void delivery() {
        System.out.println("=========================================");
        System.out.println("订单已发货,订单状态从待发货状态变成已发货状态");
        System.out.println("=========================================");
    }

    /**
     * 收货事件
     */
    @OnTransition(source = "WAITING_FOR_RECEIVE", target = "FINISH")
    public void receive() {
        System.out.println("=========================================");
        System.out.println("订单已收货,订单状态从待收货状态变成已完成状态");
        System.out.println("=========================================");
    }
}
