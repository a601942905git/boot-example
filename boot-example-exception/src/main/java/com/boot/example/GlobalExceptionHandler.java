package com.boot.example;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * com.boot.example.GlobalExceptionHandler
 *
 * @author lipeng
 * @date 2020/6/5 3:44 PM
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Response<Void> businessException(BusinessException businessException) {
        return new Response<>(businessException.getCode(), businessException.getMessage());
    }
}
