package com.boot.example;

import com.boot.example.factory.FactoryAble;

/**
 * com.boot.example.OrderService
 *
 * @author lipeng
 * @date 2019/11/7 下午5:09
 */
public interface OrderService extends FactoryAble<OrderTypeEnum> {

    /**
     * 创建订单
     */
    String createOrder();
}
