package com.boot.example.configuration;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.lang.Nullable;

/**
 * com.boot.example.configuration.BeanFactoryCacheAttributeSourceAdvisor
 *
 * 1.继承AbstractBeanFactoryPointcutAdvisor
 * 2.实现切点
 * @author lipeng
 * @date 2021/6/1 8:13 PM
 */
public class BeanFactoryCacheAttributeSourceAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    @Nullable
    private CacheAttributeSource cacheAttributeSource;

    private final CacheAttributeSourcePointcut pointcut = new CacheAttributeSourcePointcut() {
        @Override
        @Nullable
        protected CacheAttributeSource getCacheAttributeSource() {
            return cacheAttributeSource;
        }
    };

    public void setCacheAttributeSource(CacheAttributeSource cacheAttributeSource) {
        this.cacheAttributeSource = cacheAttributeSource;
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }
}
