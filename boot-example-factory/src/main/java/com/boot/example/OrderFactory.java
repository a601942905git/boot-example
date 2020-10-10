package com.boot.example;

import com.boot.example.factory.BaseFactory;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.OrderFactory
 *
 * @author lipeng
 * @date 2019/11/7 下午5:09
 */
@Component
public class OrderFactory extends BaseFactory<OrderService> {
}
