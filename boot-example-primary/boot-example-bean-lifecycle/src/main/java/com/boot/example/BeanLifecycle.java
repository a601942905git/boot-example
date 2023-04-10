package com.boot.example;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * com.boot.cloud.BeanLifecycle
 *
 * @author lipeng
 * @date 2020/6/1 7:07 PM
 */
@Log4j2
public class BeanLifecycle implements ApplicationContextAware, InitializingBean, DisposableBean {

    public BeanLifecycle() {
        log.info("execute construct method");
    }

    @PostConstruct
    public void postConstructMethod() {
        log.info("execute PostConstruct method");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("execute InitializingBean interface afterPropertiesSet method");
    }

    public void initMethod() {
        log.info("execute initMethod method");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("execute setApplicationContext method");
    }

    @PreDestroy
    public void destroyAnnotationMethod() {
        log.info("execute destroyAnnotationMethod method");
    }

    @Override
    public void destroy() throws Exception {
        log.info("execute DisposableBean interface destroy method");
    }

    public void destroyMethod() {
        log.info("execute destroyMethod method");
    }
}
