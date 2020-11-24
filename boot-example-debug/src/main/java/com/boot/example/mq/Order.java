package com.boot.example.mq;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * com.boot.example.mq.Order
 *
 * @author lipeng
 * @date 2020/11/23 7:27 PM
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Order {

    private Integer orderId;

    private String orderNo;
}
