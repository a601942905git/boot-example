package com.boot.example.order;

import lombok.*;

/**
 * com.boot.example.order.Order
 *
 * @author lipeng
 * @date 2019/1/8 上午11:10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {

    private Integer id;

    private String name;
}
