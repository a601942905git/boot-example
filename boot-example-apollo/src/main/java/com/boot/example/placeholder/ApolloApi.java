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

    public static Integer getConfig(String key) {
        return ConfigService.getAppConfig().getIntProperty(key, INTEGER_DEFAULT_VALUE);
    }
}
