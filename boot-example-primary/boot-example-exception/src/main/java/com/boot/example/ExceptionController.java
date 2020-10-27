package com.boot.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Objects;

/**
 * com.boot.example.ExceptionController
 *
 * @author lipeng
 * @date 2020/6/5 3:07 PM
 */
@RestController
public class ExceptionController {

    @RequestMapping("/exception1")
    public void exception1() {
        BusinessExceptionEnum.USER_NOT_FOUND.assertNotNull(null);
    }

    @RequestMapping("/exception2")
    public void exception2() {
        BusinessExceptionEnum.USER_LIST_IS_EMPTY.assertNotNull(null);
    }

    @RequestMapping("/exception3")
    public void exception3() {
        BusinessExceptionEnum.ORDER_LIST_IS_EMPTY.assertNotEmpty(Collections.emptyList());
    }

    @RequestMapping("/exception4")
    public void exception4() {
        Order order = Order.builder()
                .id(10001)
                .orderNo("GX20200612105800")
                .status(2)
                .build();
        // 订单不是已支付
        BusinessExceptionEnum.ORDER_IS_NOT_PAID_STATUS.assertNotFalse(() -> Objects.equals(order.getStatus(), 1));
    }
}
