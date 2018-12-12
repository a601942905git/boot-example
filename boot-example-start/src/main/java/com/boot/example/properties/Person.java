package com.boot.example.properties;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * com.boot.example.properties.Person
 *
 * @author lipeng
 * @dateTime 2018/11/22 上午9:19
 */
@Configuration
@ToString
public class Person {

    @Value("${com.person.id}")
    private Integer id;

    @Value("${com.person.name}")
    private String name;

    @Value("${com.person.age}")
    private Integer age;
}
