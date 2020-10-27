package com.boot.example.annotation;

import java.lang.annotation.*;

/**
 * com.boot.example.annotation.SystemLog
 *
 * @author lipeng
 * @date 2019-08-23 10:14
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {
}
