package com.boot.example;

import java.time.LocalDateTime;

/**
 * com.boot.example.Greeter
 *
 * @author lipeng
 * @dateTime 2018/12/13 下午4:46
 */
public class Greeter {

    private GreeterConfig greeterConfig;

    public Greeter(GreeterConfig greetingConfig) {
        this.greeterConfig = greetingConfig;
    }

    public String greet(LocalDateTime localDateTime) {

        String name = greeterConfig.getUserName();
        int hourOfDay = localDateTime.getHour();

        if (hourOfDay >= 5 && hourOfDay < 12) {
            return String.format("Hello %s, %s", name, greeterConfig.getMorningMessage());
        } else if (hourOfDay >= 12 && hourOfDay < 17) {
            return String.format("Hello %s, %s", name, greeterConfig.getAfternoonMessage());
        } else if (hourOfDay >= 17 && hourOfDay < 20) {
            return String.format("Hello %s, %s", name, greeterConfig.getEveningMessage());
        } else {
            return String.format("Hello %s, %s", name, greeterConfig.getNightMessage());
        }
    }

    public String greet() {
        return greet(LocalDateTime.now());
    }
}
