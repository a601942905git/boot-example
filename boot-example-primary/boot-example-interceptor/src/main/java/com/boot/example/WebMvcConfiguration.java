package com.boot.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * com.boot.example.WebMvcConfiguration
 *
 * @author lipeng
 * @date 2019/10/27 下午11:10
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WebRequestInterceptor()).addPathPatterns("/users/**");
    }
}
