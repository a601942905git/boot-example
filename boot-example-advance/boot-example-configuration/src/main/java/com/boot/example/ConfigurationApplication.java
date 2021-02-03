package com.boot.example;

import com.boot.example.animal.Cat;
import com.boot.example.animal.Dog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * com.boot.example.ConfigurationApplication
 *
 * @author lipeng
 * @date 2021/2/3 10:47 AM
 */
@SpringBootApplication
@Slf4j
public class ConfigurationApplication {

    private static PersonProperties personProperties;

    @Autowired
    public void setPersonProperties(PersonProperties personProperties) {
        ConfigurationApplication.personProperties = personProperties;
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext =
                SpringApplication.run(ConfigurationApplication.class, args);
        log.info("person properties：{}", personProperties);
        Dog dog = applicationContext.getBean(Dog.class);
        dog.sayHello();
        Cat cat = applicationContext.getBean(Cat.class);
        cat.sayHello();
    }
}
