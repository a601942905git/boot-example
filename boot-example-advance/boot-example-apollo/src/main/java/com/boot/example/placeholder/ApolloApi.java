package com.boot.example.placeholder;

import com.ctrip.framework.apollo.ConfigService;

/**
 * com.boot.example.placeholder.ApolloApi
 *
 * @author lipeng
 * @date 2020/8/28 11:25 AM
 */
public class ApolloApi {

    private static final Integer INTEGER_DEFAULT_VALUE = 0;

    private static final String STRING_DEFAULT_VALUE = "";

    private static final String RABBITMQ_NAMESPACE = "rabbitmq.yml";

    public static Integer getDefaultIntegerConfig(String key) {
        return ConfigService.getAppConfig().getIntProperty(key, INTEGER_DEFAULT_VALUE);
    }

    public static Integer getRabbitmqIntegerConfig(String key) {
        return ConfigService.getConfig(RABBITMQ_NAMESPACE).getIntProperty(key, INTEGER_DEFAULT_VALUE);
    }

    public static String getRabbitmqStringConfig(String key) {
        return ConfigService.getConfig(RABBITMQ_NAMESPACE).getProperty(key, STRING_DEFAULT_VALUE);
    }
}
