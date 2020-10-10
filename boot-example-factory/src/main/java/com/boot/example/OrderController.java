package com.boot.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.OrderController
 *
 * @author lipeng
 * @date 2019/11/7 下午5:22
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderFactory orderFactory;

    @RequestMapping("/create/{type}")
    public String createOrder(@PathVariable("type") Integer type) {
        return orderFactory.getBean(OrderTypeEnum.getById(type)).createOrder();
    }
}
