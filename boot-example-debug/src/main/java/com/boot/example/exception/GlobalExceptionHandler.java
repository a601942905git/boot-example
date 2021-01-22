package com.boot.example.exception;

import com.boot.example.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * com.boot.example.exception.GlobalExceptionHandler
 *
 * @author lipeng
 * @date 2021/1/15 10:44 AM
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse<?> handleMethodArgumentException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        Set<String> errorMessage = new HashSet<>();
        for (FieldError fieldError : fieldErrors) {
            errorMessage.add(fieldError.getDefaultMessage());
        }
        BaseResponse<?> baseResponse = new BaseResponse<>();
        baseResponse.setCode(10001);
        baseResponse.setMsg(errorMessage.toString());
        return baseResponse;
    }
}
