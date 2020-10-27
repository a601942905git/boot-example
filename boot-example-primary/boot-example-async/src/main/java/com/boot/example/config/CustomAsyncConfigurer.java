package com.boot.example.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

/**
 * com.boot.example.config.CustomAsyncConfigurer
 *
 * @author lipeng
 * @date 2020/7/16 2:31 PM
 */
@Configuration
public class CustomAsyncConfigurer implements AsyncConfigurer {

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncUncaughtExceptionHandler();
    }
}
