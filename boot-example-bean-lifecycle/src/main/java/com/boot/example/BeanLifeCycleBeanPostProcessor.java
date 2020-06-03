package com.boot.example;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * com.boot.cloud.lifecycle.BeanLifeCycleBeanPostProcessor
 *
 * @author lipeng
 * @date 2020/6/1 7:14 PM
 */
@Log4j2
@Component
public class BeanLifeCycleBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (Objects.equals(beanName, "beanLifecycle")) {
            log.info("execute BeanPostProcessor interface postProcessBeforeInitialization method");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (Objects.equals(beanName, "beanLifecycle")) {
            log.info("execute BeanPostProcessor interface postProcessAfterInitialization method");
        }
        return bean;
    }

    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public BeanLifecycle beanLifecycle() {
        return new BeanLifecycle();
    }
}
