package com.boot.example.config;

import org.springframework.aop.framework.autoproxy.InfrastructureAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

/**
 * com.boot.example.config.ProxySystemLogConfiguration
 *
 * @author lipeng
 * @date 2019-08-23 10:05
 */
@Configuration
public class ProxySystemLogConfiguration {

    /**
     * 定义切面
     * 此处一定要指定@Role注解
     *
     * @return
     */
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    public BeanFactorySystemLogAdvisor beanFactorySystemLogAdvisor() {
        BeanFactorySystemLogAdvisor advisor = new BeanFactorySystemLogAdvisor();
        advisor.setAdvice(systemLogInterceptor());
        return advisor;
    }

    /**
     * 定义通知
     *
     * @return
     */
    @Bean
    public SystemLogInterceptor systemLogInterceptor() {
        return new SystemLogInterceptor();
    }

    /**
     * 一定要声明InfrastructureAdvisorAutoProxyCreator，用于实现bean的后置处理
     *
     * @return
     */
    @Bean
    public InfrastructureAdvisorAutoProxyCreator infrastructureAdvisorAutoProxyCreator() {
        return new InfrastructureAdvisorAutoProxyCreator();
    }
}
