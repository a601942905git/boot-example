package com.boot.example;

import lombok.Data;

/**
 * com.boot.example.BusinessException
 *
 * @author lipeng
 * @date 2020/6/5 2:11 PM
 */
@Data
public class BusinessException extends RuntimeException {

    private Integer code;

    private String message;

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(BusinessExceptionEnum businessExceptionEnum) {
        super(businessExceptionEnum.getMessage());
        this.code = businessExceptionEnum.getCode();
        this.message = businessExceptionEnum.getMessage();
    }
}
