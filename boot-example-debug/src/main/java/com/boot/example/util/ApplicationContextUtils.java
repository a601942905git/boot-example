package com.boot.example.util;

import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

/**
 * com.boot.example.util.ApplicationContextUtils
 *
 * @author lipeng
 * @date 2020/1/6 下午6:53
 */
public class ApplicationContextUtils {

    private static ApplicationContext applicationContext;

    private static Environment environment;

    private ApplicationContextUtils() {
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ApplicationContextUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return ApplicationContextUtils.applicationContext;
    }

    public static void setEnvironment(Environment environment) {
        ApplicationContextUtils.environment = environment;
    }

    public static Environment getEnvironment() {
        return ApplicationContextUtils.environment;
    }
}
