package com.boot.example.config;

import com.boot.example.filter.CustomerPatternFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.boot.example.config.WebFilterConfigure
 *
 * @author lipeng
 * @dateTime 2018/11/22 下午4:31
 */
@Configuration
public class WebFilterConfigure {

    @Bean
    public FilterRegistrationBean webFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

        filterRegistrationBean.setFilter(new CustomerPatternFilter());
        filterRegistrationBean.addUrlPatterns("/users/*");
        filterRegistrationBean.setOrder(3);

        return filterRegistrationBean;
    }
}
