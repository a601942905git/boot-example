package com.boot.example.designPattern.strategy.service.impl;

import com.boot.example.designPattern.strategy.enums.OrderTypeEnum;
import com.boot.example.designPattern.strategy.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * com.boot.example.designPattern.strategy.service.impl.GroupOrderServiceImpl
 *
 * @author lipeng
 * @date 2020/12/22 4:52 PM
 */
@Service
@Slf4j
public class GroupOrderServiceImpl implements OrderService {

    @Override
    public OrderTypeEnum supportType() {
        return OrderTypeEnum.GROUP_ORDER;
    }

    @Override
    public String handleOrder() {
        return "处理拼团订单";
    }
}
