package com.boot.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * com.boot.example.DiscountOrderServiceImpl
 *
 * @author lipeng
 * @date 2019/11/7 下午5:12
 */
@Service
@Slf4j
public class DiscountOrderServiceImpl implements OrderService {

    @Override
    public String createOrder() {
        return "create discount order";
    }

    @Override
    public List<OrderTypeEnum> supportType() {
        return Collections.singletonList(OrderTypeEnum.DISCOUNT_ORDER);
    }
}
