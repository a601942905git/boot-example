package com.boot.example.animal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.boot.example.AnimalProperties
 *
 * 1.proxyBeanMethods设置为true，会返回共享单例Bean
 * 2.proxyBeanMethods设置为false，通过dog()方式会重新创建Bean
 *
 * @author lipeng
 * @date 2021/2/3 3:08 PM
 */
@Configuration
public class AnimalProperties {

    @Bean
    public Dog dog() {
        return new Dog(10001, "dog");
    }

    @Bean
    public Cat cat() {
        return new Cat(10002, "cat", dog());
    }

    @Bean
    public Tiger tiger(Dog dog) {
        return new Tiger(10003, "tiger", dog);
    }
}
