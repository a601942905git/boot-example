package com.boot.example.designPattern.strategy.response;

import lombok.Data;

/**
 * com.boot.example.designPattern.strategy.response.OrderCreateResponse
 *
 * @author lipeng
 * @date 2020/12/22 5:07 PM
 */
@Data
public class OrderCreateResponse<T> {

    private String code;

    private String msg;

    private T data;

    public static <T> OrderCreateResponse<T> success(String code, String msg, T data) {
        OrderCreateResponse<T> orderCreateResponse = new OrderCreateResponse();
        orderCreateResponse.setCode(code);
        orderCreateResponse.setMsg(msg);
        orderCreateResponse.setData(data);
        return orderCreateResponse;
    }
}
