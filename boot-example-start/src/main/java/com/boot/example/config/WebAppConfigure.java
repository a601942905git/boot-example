package com.boot.example.config;

import com.boot.example.interceptor.CustomInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * com.boot.example.config.WebAppConfigure
 *
 * @author lipeng
 * @dateTime 2018/11/22 下午3:55
 */
@Configuration
public class WebAppConfigure implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomInterceptor()).addPathPatterns("/users/*");
    }
}
