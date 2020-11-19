package com.boot.example.config;

import com.boot.example.filter.RequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.boot.example.config.WebConfig
 *
 * @author lipeng
 * @date 2020/11/19 10:40 AM
 */
@Configuration
public class WebConfig {

    /**
     * 内嵌Tomcat在启动的时候会获取所有类型为ServletContextInitializer的Bean，然后添加Filter和Servlet
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<RequestFilter> filterRegistrationBean() {
        FilterRegistrationBean<RequestFilter> filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new RequestFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
