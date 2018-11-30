package com.boot.example.response;

import lombok.*;

/**
 * com.boot.example.response.BaseResponse
 *
 * @author lipeng
 * @dateTime 2018/11/30 上午10:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseResponse <T> {

    private String code;

    private String message;

    public T result;
}
