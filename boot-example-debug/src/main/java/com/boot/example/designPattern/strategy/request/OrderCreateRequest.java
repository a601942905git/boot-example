package com.boot.example.designPattern.strategy.request;

import com.boot.example.designPattern.strategy.enums.OrderTypeEnum;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * com.boot.example.designPattern.strategy.request.OrderCreateRequest
 *
 * @author lipeng
 * @date 2020/12/22 5:06 PM
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderCreateRequest {

    private OrderTypeEnum orderType;
}
