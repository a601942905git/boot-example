package com.boot.example.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * com.boot.example.security.CustomerWebMvcConfigure
 *
 * @author lipeng
 * @dateTime 2018/12/14 下午4:27
 */
@Configuration
public class CustomerWebMvcConfigure implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }
}
