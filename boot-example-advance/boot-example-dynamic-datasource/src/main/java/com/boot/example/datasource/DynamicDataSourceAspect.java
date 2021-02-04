package com.boot.example.datasource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * com.boot.example.datasource.DynamicDataSourceAspect
 *
 * @author lipeng
 * @date 2021/2/4 10:32 AM
 */
@Component
@Aspect
public class DynamicDataSourceAspect {

    @Pointcut("@annotation(com.boot.example.datasource.DynamicDataSource)")
    public void dynamicDataSourcePointCut(){

    }

    @Around("dynamicDataSourcePointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        DynamicDataSource dynamicDataSource = getDataSourceAnnotation(joinPoint);
        // 默认走master
        String dynamicDataSourceKey = dynamicDataSource.value();
        DynamicDataSourceContextHolder.set(dynamicDataSourceKey);
        try{
            return joinPoint.proceed();
        }finally {
            DynamicDataSourceContextHolder.remove();
        }
    }

    /**
     * 根据类或方法获取数据源注解
     */
    private DynamicDataSource getDataSourceAnnotation(ProceedingJoinPoint joinPoint){
        Class<?> targetClass = joinPoint.getTarget().getClass();

        // 类注解
        DynamicDataSource dynamicDataSource = targetClass.getAnnotation(DynamicDataSource.class);
        if (Objects.nonNull(dynamicDataSource)) {
            return dynamicDataSource;
        }

        // 方法注解
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod().getAnnotation(DynamicDataSource.class);
    }
}
