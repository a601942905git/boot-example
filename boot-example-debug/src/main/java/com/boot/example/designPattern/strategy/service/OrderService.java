package com.boot.example.designPattern.strategy.service;

import com.boot.example.designPattern.strategy.enums.OrderTypeEnum;

/**
 * com.boot.example.designPattern.strategy.OrderService
 *
 * @author lipeng
 * @date 2020/12/22 4:46 PM
 */
public interface OrderService {

    OrderTypeEnum supportType();

    String handleOrder();
}
