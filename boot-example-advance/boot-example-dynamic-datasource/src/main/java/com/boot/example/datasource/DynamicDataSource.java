package com.boot.example.datasource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * com.boot.example.datasource.DataSource
 *
 * @author lipeng
 * @date 2021/2/4 10:30 AM
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DynamicDataSource {

    String value() default DynamicDataSourceConstants.DYNAMIC_DATA_SOURCE_KEY_MASTER;
}
