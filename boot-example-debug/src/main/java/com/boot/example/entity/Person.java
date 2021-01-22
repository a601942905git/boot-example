package com.boot.example.entity;

import com.boot.example.annotation.Greet;
import lombok.Data;

/**
 * com.boot.example.entity.Person
 *
 * @author lipeng
 * @date 2021/1/22 4:45 PM
 */
@Data
public class Person {

    @Greet
    public String say() {
        return "say";
    }

    @Greet
    public String hello() {
        return "hello";
    }

    @Greet
    public String hi() {
        return "hi";
    }

    public String doHomeWork() {
        return "do home work";
    }

    public String doRun() {
        return "do run";
    }
}
