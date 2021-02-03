package com.boot.example;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * com.boot.example.PersonAutoConfiguration
 *
 * 1.@EnableConfigurationProperties注解会把value指定的class注入到容器中
 * 2.ConfigurationPropertiesBindingPostProcessor处理器的前置通知处理含有@ConfigurationProperties注解的Bean
 * 3.根据@ConfigurationProperties注解的prefix + 字段名称组成要获取配置的key，从PropertySources获取对应的value值
 *
 *
 * @author lipeng
 * @date 2021/2/3 10:46 AM
 */
@Configuration
@EnableConfigurationProperties(PersonProperties.class)
public class PersonAutoConfiguration {

}
