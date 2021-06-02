package com.boot.example.configuration;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.configuration.CacheInterceptor
 *
 * @author lipeng
 * @date 2021/6/1 8:14 PM
 */
@Slf4j
public class CacheInterceptor implements MethodInterceptor {

    private CacheAttributeSource cacheAttributeSource;

    private StringRedisTemplate stringRedisTemplate;

    public CacheInterceptor(CacheAttributeSource cacheAttributeSource, StringRedisTemplate stringRedisTemplate) {
        this.cacheAttributeSource = cacheAttributeSource;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Class<?> targetClass = invocation.getMethod().getDeclaringClass();
        CacheAttribute cacheAttribute = cacheAttributeSource.getCacheAttribute(method, targetClass);
        String cacheKey = cacheAttribute.getName();
        String cacheResult = stringRedisTemplate.opsForValue().get(cacheKey);
        log.info("cache hint，result：{}", cacheResult);
        if (StringUtils.hasText(cacheResult)) {
            return JSONObject.parse(cacheResult);
        }

        ThreadLocalRandom random = ThreadLocalRandom.current();
        // 执行目标方法
        Object methodExecuteResult = invocation.proceed();
        long timeout = cacheAttribute.getTtl() + random.nextLong(cacheAttribute.getRandomTtl());
        log.info("cache timeout：{}", timeout);
        stringRedisTemplate.opsForValue().set(cacheKey, JSONObject.toJSONString(methodExecuteResult), timeout, TimeUnit.SECONDS);
        return methodExecuteResult;
    }

}
