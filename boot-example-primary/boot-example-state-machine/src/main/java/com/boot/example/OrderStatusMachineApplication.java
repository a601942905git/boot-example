package com.boot.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

/**
 * com.boot.example.OrderStatusMachineApplication
 *
 * @author lipeng
 * @date 2018/12/25 下午5:30
 */
@SpringBootApplication
public class OrderStatusMachineApplication {

    private static StateMachine<OrderStatusEnum, OrderEventEnum> stateMachine;

    @Autowired
    public void setStateMachine(StateMachine<OrderStatusEnum, OrderEventEnum> stateMachine) {
        OrderStatusMachineApplication.stateMachine = stateMachine;
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderStatusMachineApplication.class, args);
        // 启动状态机
        stateMachine.start();
        // 支付
        stateMachine.sendEvent(OrderEventEnum.PAY);
        // 发货
        stateMachine.sendEvent(OrderEventEnum.DELIVERY);
        // 收货
        stateMachine.sendEvent(OrderEventEnum.RECEIVE);
    }
}
