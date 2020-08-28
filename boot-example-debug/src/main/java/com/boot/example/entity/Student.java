package com.boot.example.entity;

import com.boot.example.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * com.boot.example.Student
 *
 * @author lipeng
 * @date 2020/7/2 3:12 PM
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Student extends Person {

    private Integer id;

    private String name;

    public String sayHello() {
        return "i am a teacher，id：" + this.id + "，name：" + this.name;
    }
}
