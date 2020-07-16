package com.boot.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * com.boot.example.config.CustomAsyncUncaughtExceptionHandler
 *
 * @author lipeng
 * @date 2020/7/16 2:32 PM
 */
@Slf4j
public class CustomAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        log.error("method：{}，param：{}，async execute exception：", method.getName(), params, ex);
    }
}
