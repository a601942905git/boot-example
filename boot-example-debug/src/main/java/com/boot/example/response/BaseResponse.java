package com.boot.example.response;

import lombok.Data;

/**
 * com.boot.example.response.BaseResponse
 *
 * @author lipeng
 * @date 2021/1/15 10:48 AM
 */
@Data
public class BaseResponse<T> {

    private Integer code;

    private String msg;

    private T data;
}
