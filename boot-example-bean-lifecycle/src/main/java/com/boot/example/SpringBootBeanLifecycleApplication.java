package com.boot.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * com.boot.example.SpringBootBeanLifecycleApplication
 *
 * @author lipeng
 * @date 2019-03-20 10:15
 */
@SpringBootApplication
public class SpringBootBeanLifecycleApplication {

    private static User user;

    @Autowired
    public void setUser(User user) {
        SpringBootBeanLifecycleApplication.user = user;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBeanLifecycleApplication.class, args);
        user.getName();
    }

    @Bean(initMethod = "initMethod", destroyMethod = "afterDestroy")
    public User user() {
        return new User(10002, "smile");
    }
}
