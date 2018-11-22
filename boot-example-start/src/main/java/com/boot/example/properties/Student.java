package com.boot.example.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.properties.Person1
 *
 * @author lipeng
 * @dateTime 2018/11/22 上午9:55
 */
@Component
@ConfigurationProperties(prefix = "com.person")
@Data
@ToString
public class Student {

    private Integer id;

    private String name;

    private Integer age;
}
