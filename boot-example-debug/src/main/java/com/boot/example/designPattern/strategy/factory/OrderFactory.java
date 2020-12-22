package com.boot.example.designPattern.strategy.factory;

import com.boot.example.designPattern.strategy.enums.OrderTypeEnum;
import com.boot.example.designPattern.strategy.service.OrderService;
import com.boot.example.util.ApplicationContextUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * com.boot.example.designPattern.strategy.factory.OrderFactory
 *
 * @author lipeng
 * @date 2020/12/22 5:00 PM
 */
@Component
public class OrderFactory implements InitializingBean {

    private final Map<OrderTypeEnum, OrderService> orderServiceMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        Collection<OrderService> orderServiceCollection = ApplicationContextUtils.getApplicationContext()
                .getBeansOfType(OrderService.class).values();
        for (OrderService orderService : orderServiceCollection) {
            orderServiceMap.put(orderService.supportType(), orderService);
        }
    }

    public OrderService getOrderService(OrderTypeEnum orderTypeEnum) {
        return orderServiceMap.get(orderTypeEnum);
    }
}
