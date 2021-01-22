package com.boot.example;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.boot.example.WebFilterConfiguration
 *
 * @author lipeng
 * @date 2019/10/27 下午8:41
 */
@Configuration
public class WebFilterConfiguration {

    /**
     * 1.org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext#getSelfInitializer()
     * 定义ServletContextInitializer
     *
     * 2.org.springframework.boot.web.embedded.tomcat.TomcatStarter#onStartup(java.util.Set, javax.servlet.ServletContext)
     * 调用ServletContextInitializer的onStartup()
     *
     * 3.ServletContextInitializerBeans#addServletContextInitializerBeans(ListableBeanFactory)方法会从IOC容器中获取类型为
     * ServletContextInitializer的Bean，之后添加到ServletContext中
     *
     *
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean<CustomFilter2> filterRegistrationBean() {
        FilterRegistrationBean<CustomFilter2> filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new CustomFilter2());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(2);
        return filterRegistrationBean;
    }
}
