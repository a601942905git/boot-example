package com.boot.example.config;

import com.boot.example.BaseResponse;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * com.boot.example.config.GlobalExceptionHandler
 * 全局异常处理
 * @author lipeng
 * @date 2018/12/19 下午1:32
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse authenticationExceptionHandler() {
        return BaseResponse.error(Result.ResultCodeEnum.LOGIN_AUTHENTICATION_FAIL.getCode(),
                Result.ResultMessageEnum.LOGIN_AUTHENTICATION_FAIL.getMessage());
    }
}
