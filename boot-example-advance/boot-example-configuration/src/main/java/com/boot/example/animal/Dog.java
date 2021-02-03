package com.boot.example.animal;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * com.boot.example.animal.Dog
 *
 * @author lipeng
 * @date 2021/2/3 4:11 PM
 */
@Data
@Slf4j
public class Dog {

    private Integer id;

    private String name;

    public Dog(Integer id, String name) {
        this.id = id;
        this.name = name;
        log.info("execute dog construct");
    }

    public void sayHello() {
        log.info("execute dog say hello......");
    }
}
