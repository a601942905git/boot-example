package com.boot.example.exception;

import com.boot.example.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * com.boot.example.exception.CustomerExceptionHandler
 *
 * @author lipeng
 * @dateTime 2018/11/30 上午10:46
 */
@ControllerAdvice
@ResponseBody
public class CustomerExceptionHandler {

    @ExceptionHandler(CustomerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse handlerCustomerException(CustomerException ex) {
        BaseResponse baseResponse = BaseResponse.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .build();
        return baseResponse;
    }
}
