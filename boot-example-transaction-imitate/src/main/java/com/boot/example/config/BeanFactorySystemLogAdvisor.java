package com.boot.example.config;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * com.boot.example.config.SystemLogAdvisor
 *
 * @author lipeng
 * @date 2019-08-23 10:06
 */
public class BeanFactorySystemLogAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    /**
     * 定义切点
     */
    private final SystemLogPointcut point = new SystemLogPointcut();

    @Override
    public Pointcut getPointcut() {
        return this.point;
    }
}
