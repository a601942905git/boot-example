package com.boot.example.designPattern.strategy.service.impl;

import com.boot.example.designPattern.strategy.enums.OrderTypeEnum;
import com.boot.example.designPattern.strategy.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * com.boot.example.designPattern.strategy.NormalOrderServiceImpl
 *
 * @author lipeng
 * @date 2020/12/22 4:50 PM
 */
@Service
@Slf4j
public class NormalOrderServiceImpl implements OrderService {

    @Override
    public OrderTypeEnum supportType() {
        return OrderTypeEnum.NORMAL_ORDER;
    }

    @Override
    public String handleOrder() {
        return "处理正常订单";
    }
}
