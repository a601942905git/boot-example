package com.boot.example.configuration;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
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
        String cacheName = cacheAttribute.getName();
        String cacheKey = cacheName + ":" + parseKey(cacheAttribute.getKey(), method, invocation.getArguments());
        String cacheResult = stringRedisTemplate.opsForValue().get(cacheKey);
        log.info("cache hint，result：{}", cacheResult);
        if (StringUtils.hasText(cacheResult)) {
            return JSONObject.parse(cacheResult);
        }

        ThreadLocalRandom random = ThreadLocalRandom.current();
        // 执行ReflectiveMethodInvocation的proceed()方法，如果没有通知要执行，则会执行目标方法
        Object methodExecuteResult = invocation.proceed();
        long timeout = cacheAttribute.getTtl() + random.nextLong(cacheAttribute.getRandomTtl());
        log.info("cache timeout：{}", timeout);
        stringRedisTemplate.opsForValue().set(cacheKey, JSONObject.toJSONString(methodExecuteResult), timeout, TimeUnit.SECONDS);
        return methodExecuteResult;
    }

    private String parseKey(String key, Method method, Object [] args){

        if(!StringUtils.hasText(key)){
            return null;
        }

        // 获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);

        // 使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        // SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        // 把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context, String.class);
    }
}
