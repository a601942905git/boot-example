package com.boot.example.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.properties.Teacher
 *
 * @author lipeng
 * @dateTime 2018/11/22 上午11:09
 */
@Component
@PropertySource(value = "classpath:test.properties", encoding = "UTF-8")
@Data
@ToString
public class Teacher {

    @Value("${com.teacher.id}")
    private Integer id;

    @Value("${com.teacher.name}")
    private String name;

    @Value("${com.teacher.sex}")
    private String sex;
}
