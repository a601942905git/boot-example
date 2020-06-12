package com.boot.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * com.boot.example.Order
 *
 * @author lipeng
 * @date 2020/6/12 10:56 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Integer id;

    private String orderNo;

    /**
     * 1.代表订单已支付
     */
    private Integer status;
}
