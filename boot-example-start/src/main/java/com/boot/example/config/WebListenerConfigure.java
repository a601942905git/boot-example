package com.boot.example.config;

import com.boot.example.listener.CustomerListener5;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.boot.example.config.SystemListenerConfigure
 * 系统监听器配置类
 * @author lipeng
 * @dateTime 2018/12/10 下午3:47
 */
@Configuration
public class WebListenerConfigure {

    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean() {
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new CustomerListener5());
        return servletListenerRegistrationBean;
    }
}
