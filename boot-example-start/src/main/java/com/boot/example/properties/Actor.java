package com.boot.example.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.properties.Actor
 *
 * @author lipeng
 * @dateTime 2018/11/22 上午11:13
 */
@Component
@PropertySource(value = "test.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "com.teacher")
@Data
@ToString
public class Actor {

    private Integer id;

    private String name;

    private String sex;
}
