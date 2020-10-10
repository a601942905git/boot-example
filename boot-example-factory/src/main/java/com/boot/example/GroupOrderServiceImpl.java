package com.boot.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * com.boot.example.GroupOrderServiceImpl
 *
 * @author lipeng
 * @date 2019/11/7 下午5:10
 */
@Service
@Slf4j
public class GroupOrderServiceImpl implements OrderService {

    @Override
    public String createOrder() {
        return "create group order";
    }

    @Override
    public List<OrderTypeEnum> supportType() {
        return Collections.singletonList(OrderTypeEnum.GROUP_ORDER);
    }
}
