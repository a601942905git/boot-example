package com.boot.example.designPattern.strategy.controller;

import com.boot.example.designPattern.strategy.factory.OrderFactory;
import com.boot.example.designPattern.strategy.request.OrderCreateRequest;
import com.boot.example.designPattern.strategy.response.OrderCreateResponse;
import com.boot.example.designPattern.strategy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.designPattern.strategy.controller.OrderController
 *
 * @author lipeng
 * @date 2020/12/22 5:05 PM
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderFactory orderFactory;

    @PostMapping("/create")
    public OrderCreateResponse<?> createOrder(@RequestBody OrderCreateRequest request) {
        OrderService orderService = orderFactory.getOrderService(request.getOrderType());
        String result = orderService.handleOrder();
        return OrderCreateResponse.success("0", result, null);
    }
}
