package com.boot.example;

/**
 * com.boot.example.OrderStatusEnum
 * 订单状态枚举类
 * @author lipeng
 * @date 2018/12/25 下午5:16
 */
public enum  OrderStatusEnum {

    /**
     * 已下单未付款状态
     */
    UNPAID,

    /**
     * 已付款未发货状态
     */
    WAITING_FOR_DELIVERY,

    /**
     * 已发货未收货状态
     */
    WAITING_FOR_RECEIVE,

    /**
     * 已收货订单完成状态
     */
    FINISH;
}
