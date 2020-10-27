package com.boot.example.config;

import com.boot.example.annotation.SystemLog;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * com.boot.example.config.SystemLogPointcut
 *
 * @author lipeng
 * @date 2019-08-23 10:07
 */
public class SystemLogPointcut extends StaticMethodMatcherPointcut {

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        AnnotationAttributes attributes = AnnotatedElementUtils.findMergedAnnotationAttributes(
                targetClass, SystemLog.class, false, false);
        if (Objects.nonNull(attributes)) {
            return true;
        }

        attributes = AnnotatedElementUtils.findMergedAnnotationAttributes(
                method, SystemLog.class, false, false);
        return Objects.nonNull(attributes);
    }
}
