package com.boot.example.animal;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * com.boot.example.animal.Tiger
 *
 * @author lipeng
 * @date 2021/2/3 4:24 PM
 */
@Data
@Slf4j
public class Tiger {

    private Integer id;

    private String name;

    private Dog dog;

    public Tiger(Integer id, String name, Dog dog) {
        this.id = id;
        this.name = name;
        log.info("execute tiger construct");
    }

    public void sayHello() {
        log.info("execute tiger say hello......");
    }
}
