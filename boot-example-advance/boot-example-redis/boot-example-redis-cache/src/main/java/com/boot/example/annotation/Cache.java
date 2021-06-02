package com.boot.example.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * com.boot.example.annotation.Cache
 *
 * @author lipeng
 * @date 2021/6/1 8:06 PM
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Cache {

    /**
     * 缓存名称
     *
     * @return 缓存名称
     */
    String name() default "";

    /**
     * 缓存key
     *
     * @return 缓存key
     */
    String key() default "";

    /**
     * 缓存过期时间
     *
     * @return 缓存过期时间
     */
    long ttl() default 300;

    /**
     * 随机时间
     *
     * @return 随机时间
     */
    long randomTtl() default 120;
}
