package com.boot.example.entity;

import lombok.Data;

/**
 * com.boot.example.Person
 *
 * @author lipeng
 * @date 2020/7/2 3:11 PM
 */
@Data
public class Teacher {

    private Integer id;

    private String name;

    public String sayHello() {
        return "i am a teacher，id：" + this.id + "，name：" + this.name;
    }
}
