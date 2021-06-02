package com.boot.example.configuration;

import com.boot.example.annotation.Cache;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.MethodClassKey;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * com.boot.example.configuration.AnnotationCacheAttributeSource
 *
 * @author lipeng
 * @date 2021/6/2 10:14 AM
 */
public class AnnotationCacheAttributeSource implements CacheAttributeSource {

    private final Map<Object, CacheAttribute> attributeCache = new ConcurrentHashMap<>(1024);

    @Override
    public CacheAttribute getCacheAttribute(Method method, Class<?> targetClass) {
        if (method.getDeclaringClass() == Object.class) {
            return null;
        }

        // First, see if we have a cached value.
        Object cacheKey = getCacheKey(method, targetClass);
        CacheAttribute cached = this.attributeCache.get(cacheKey);
        if (cached != null) {
            return cached;
        }

        CacheAttribute cacheAttribute = computeCacheAttribute(method, targetClass);

        if (Objects.isNull(cacheAttribute)) {
            return null;
        }
        this.attributeCache.put(cacheKey, cacheAttribute);
        return cacheAttribute;
    }

    private CacheAttribute computeCacheAttribute(Method method, Class<?> targetClass) {
        Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);

        // First try is the method in the target class.
        CacheAttribute cacheAttribute = findCacheAttribute(specificMethod);
        if (cacheAttribute != null) {
            return cacheAttribute;
        }

        // Second try is the transaction attribute on the target class.
        cacheAttribute = findCacheAttribute(specificMethod.getDeclaringClass());
        if (cacheAttribute != null) {
            return cacheAttribute;
        }

        return null;
    }

    private CacheAttribute findCacheAttribute(AnnotatedElement element) {
        AnnotationAttributes attributes = AnnotatedElementUtils.findMergedAnnotationAttributes(
                element, Cache.class, false, false);
        if (attributes != null) {
            return parseTransactionAnnotation(attributes);
        }
        return null;
    }

    private CacheAttribute parseTransactionAnnotation(AnnotationAttributes attributes) {
        CacheAttribute cacheAttribute = new CacheAttribute();
        cacheAttribute.setName(attributes.getString("name"));
        cacheAttribute.setKey(attributes.getString("key"));
        cacheAttribute.setTtl(attributes.getNumber("ttl"));
        cacheAttribute.setRandomTtl(attributes.getNumber("randomTtl"));
        return cacheAttribute;
    }

    private Object getCacheKey(Method method, Class<?> targetClass) {
        return new MethodClassKey(method, targetClass);
    }
}
