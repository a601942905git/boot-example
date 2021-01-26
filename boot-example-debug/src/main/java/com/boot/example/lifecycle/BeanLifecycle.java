package com.boot.example.lifecycle;

import com.boot.example.service.DebugService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.Lifecycle;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * com.boot.example.lifecycle.BeanLifecycle
 *
 * @author lipeng
 * @date 2020/11/3 5:50 PM
 */
@Component
@Slf4j
public class BeanLifecycle implements ApplicationContextAware, EnvironmentAware, InitializingBean, DisposableBean, Lifecycle, SmartInitializingSingleton {

    private ApplicationContext applicationContext;

    private Environment environment;

    @Value("${inject.value}")
    private String injectValue;

    @Autowired
    private DebugService debugService;

    public BeanLifecycle() {
        log.error("execute construct method");
    }

    @PostConstruct
    public void postConstructMethod() {
        log.error("execute PostConstruct method");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.error("execute InitializingBean interface afterPropertiesSet method");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        log.error("execute setApplicationContext method");
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        log.error("execute setEnvironment method");
    }

    @PreDestroy
    public void destroyAnnotationMethod() {
        log.error("execute destroyAnnotationMethod method");
    }

    @Override
    public void destroy() throws Exception {
        log.error("execute DisposableBean interface destroy method");
    }

    @Override
    public void afterSingletonsInstantiated() {
        GenericApplicationContext genericApplicationContext = (GenericApplicationContext) applicationContext;
        log.error("container single bean count：{}", genericApplicationContext.getBeanFactory().getSingletonCount());
    }

    @Override
    public void start() {
        log.error("the application start");
    }

    @Override
    public void stop() {
        log.error("the application stop");
    }

    @Override
    public boolean isRunning() {
        return true;
    }
}
