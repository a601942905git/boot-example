package com.boot.example.designPattern.strategy.service.impl;

import com.boot.example.designPattern.strategy.enums.OrderTypeEnum;
import com.boot.example.designPattern.strategy.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * com.boot.example.designPattern.strategy.service.impl.DiscountOrderServiceImpl
 *
 * @author lipeng
 * @date 2020/12/22 4:51 PM
 */
@Service
@Slf4j
public class DiscountOrderServiceImpl implements OrderService {

    @Override
    public OrderTypeEnum supportType() {
        return OrderTypeEnum.DISCOUNT_ORDER;
    }

    @Override
    public String handleOrder() {
        return "处理折扣订单";
    }
}
