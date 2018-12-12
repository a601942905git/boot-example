package com.boot.example.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * com.boot.example.properties.Actor
 *
 * @author lipeng
 * @dateTime 2018/11/22 上午11:13
 */
@Configuration
@PropertySource(value = "classpath:test.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "com.teacher")
@Data
@ToString
public class Actor {

    private Integer id;

    private String name;

    private String sex;
}
