package com.boot.example.convert;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * com.boot.example.convert.AppAutoConfigure
 *
 * @author lipeng
 * @date 2019/1/9 下午1:17
 */
@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class AppAutoConfigure {
}
