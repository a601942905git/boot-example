package com.boot.example.factory;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.factory.CustomAdvice
 *
 * @author lipeng
 * @date 2021/1/25 8:43 PM
 */
@Slf4j
@Component
public class TigerAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("======执行目标方法开始======");
        Object result = invocation.proceed();
        log.info("target method result：{}", result);
        log.info("======执行目标方法结束======");
        return result;
    }
}
