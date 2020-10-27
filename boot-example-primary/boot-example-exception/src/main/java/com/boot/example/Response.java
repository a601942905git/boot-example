package com.boot.example;

import lombok.Data;

/**
 * com.boot.example.Response
 *
 * @author lipeng
 * @date 2020/6/5 3:45 PM
 */
@Data
public class Response<T> {

    private Integer code;

    private String message;

    private T info;

    public Response(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
