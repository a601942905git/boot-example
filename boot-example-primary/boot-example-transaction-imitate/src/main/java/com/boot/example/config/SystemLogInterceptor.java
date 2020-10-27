package com.boot.example.config;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.Clock;
import java.util.Arrays;

/**
 * com.boot.example.config.SystemLogInterceptor
 *
 * @author lipeng
 * @date 2019-08-23 10:19
 */
@Slf4j
public class SystemLogInterceptor implements MethodInterceptor, Serializable {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();
        log.info("======[" + className + "#" + methodName + " method begin execute]======");
        Arrays.stream(invocation.getArguments()).forEach(argument -> log.info("======[execute method argument：" + argument + "]======"));
        Long time1 = Clock.systemDefaultZone().millis();
        Object result = invocation.proceed();
        Long time2 = Clock.systemDefaultZone().millis();
        log.info("======[method execute time：" + (time2 - time1) + "]======");
        return result;
    }
}
