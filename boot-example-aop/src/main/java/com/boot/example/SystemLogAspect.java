package com.boot.example;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.Clock;
import java.util.Arrays;

/**
 * com.boot.example.SystemLogAspect
 *
 * @author lipeng
 * @date 2018/12/26 上午9:36
 */
@Component
@Aspect
@Slf4j
public class SystemLogAspect {

    /**
     * 定义切入点
     */
    @Pointcut("execution(* com.boot.example.controller.*.*(..))")
    public void SystemLogAspect() {
    }

    /**
     * 方法执行之前通知
     *
     * @param joinPoint
     */
    @Before("SystemLogAspect()")
    public void doBefore(JoinPoint joinPoint) {
    }

    /**
     * 方法返回结果后通知
     */
    @AfterReturning("SystemLogAspect()")
    public void doAfterReturning() {

    }

    /**
     * 方法环绕通知
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Around("SystemLogAspect()")
    public void doAround(ProceedingJoinPoint joinPoint) {
        // 记录请求开始时间
        Long startTime = Clock.systemDefaultZone().millis();
        Signature signature = joinPoint.getSignature();
        HttpServletRequest request = WebContextUtils.getRequest();
        log.info("==================请求开始=====================");
        log.info("Request IP:{}", request.getRemoteAddr());
        log.info("Request URL:{}", request.getRequestURL());
        log.info("Request Method:{}", request.getMethod());
        log.info("Request Class Method:{}", signature.getDeclaringTypeName() + "#" + signature.getName());
        log.info("Request Method Args:{}", Arrays.toString(joinPoint.getArgs()));

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        log.info("方法执行结果:{}", result);

        Long diffSystemMillis = Clock.systemDefaultZone().millis() - startTime;
        log.info("Request Execute Time:{}", diffSystemMillis);
        log.info("==================请求结束=====================\n");
    }

    /**
     * 方法异常通知
     *
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(value = "SystemLogAspect()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        Signature signature = joinPoint.getSignature();
        log.info("Request Class Method:{}",
                signature.getDeclaringTypeName() +
                        "#" + signature.getName() + ",Throw Exception" + ex.getMessage());
    }
}
