package com.boot.example.entity;

import lombok.Data;
import org.springframework.context.annotation.Bean;

/**
 * com.boot.example.Animal
 *
 * @author lipeng
 * @date 2020/7/2 3:32 PM
 */
@Data
public class Animal {

    private Integer id;

    private String name;

    public String sayHello() {
        return "i am a animal，id：" + this.id + "，name：" + this.name;
    }

    @Bean
    public Dog dog() {
        return new Dog(10004, "create dog by @Bean method");
    }

    @Bean
    public Cat cat() {
        return new Cat(10004, "create cat by @Bean method");
    }
}
