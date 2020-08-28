package com.boot.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * com.boot.example.Dog
 *
 * @author lipeng
 * @date 2020/7/2 3:33 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dog {

    private Integer id;

    private String name;

    public String sayHello() {
        return "i am a animal，id：" + this.id + "，name：" + this.name;
    }
}
