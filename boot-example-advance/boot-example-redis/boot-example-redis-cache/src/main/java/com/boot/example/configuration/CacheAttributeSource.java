package com.boot.example.configuration;

import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

/**
 * com.boot.example.configuration.CacheAttributeSource
 *
 * @author lipeng
 * @date 2021/6/1 8:14 PM
 */
public interface CacheAttributeSource {

    @Nullable
    CacheAttribute getCacheAttribute(Method method, @Nullable Class<?> targetClass);
}
