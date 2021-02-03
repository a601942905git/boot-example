package com.boot.example.animal;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * com.boot.example.animal.Cat
 *
 * @author lipeng
 * @date 2021/2/3 4:12 PM
 */
@Data
@Slf4j
public class Cat {

    private Integer id;

    private String name;

    private Dog dog;

    public Cat(Integer id, String name, Dog dog) {
        this.id = id;
        this.name = name;
        log.info("execute cat construct");
    }

    public void sayHello() {
        log.info("execute cat say hello......");
    }
}
