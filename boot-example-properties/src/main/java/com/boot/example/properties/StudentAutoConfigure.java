package com.boot.example.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * com.boot.example.properties.StudentAutoConfigure
 *
 * @author lipeng
 * @date 2019/1/9 上午10:50
 */
@Configuration
@EnableConfigurationProperties(StudentProperties.class)
public class StudentAutoConfigure {
}
