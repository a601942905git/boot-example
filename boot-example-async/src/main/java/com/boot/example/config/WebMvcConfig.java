package com.boot.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * com.boot.example.config.WebMvcConfig
 *
 * @author lipeng
 * @date 2019/10/9 下午5:45
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 异步配置
     * 设置线程池、超时时间
     *
     * @param configurer 异步配置兑现
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(threadPoolTaskExecutor);
        configurer.setDefaultTimeout(3000);
    }
}
