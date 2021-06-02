package com.boot.example.configuration;

import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

/**
 * com.boot.example.configuration.CacheAttributeSourcePointcut
 *
 * 1.继承StaticMethodMatcherPointcut
 * 2.重写matches()方法
 * @author lipeng
 * @date 2021/6/2 10:01 AM
 */
public abstract class CacheAttributeSourcePointcut extends StaticMethodMatcherPointcut {

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        CacheAttributeSource cas = getCacheAttributeSource();
        return (cas == null || cas.getCacheAttribute(method, targetClass) != null);
    }

    @Nullable
    protected abstract CacheAttributeSource getCacheAttributeSource();
}
