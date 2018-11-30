package com.boot.example.exception;

import lombok.Data;

/**
 * com.boot.example.exception.CustomerException
 *
 * @author lipeng
 * @dateTime 2018/11/30 上午10:38
 */
@Data
public class CustomerException extends RuntimeException{

    private String code;

    private String message;

    public CustomerException() {
    }

    public CustomerException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
