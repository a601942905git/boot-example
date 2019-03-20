package com.boot.example;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * com.boot.example.User
 *
 * @author lipeng
 * @date 2019-03-20 10:16
 */
public class User implements InitializingBean, DisposableBean {

    private Integer id;

    private String name;

    public User() {
        System.out.println("【调用无参构造】");
    }

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
        System.out.println("【调用有参构造】");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        System.out.println("【调用setId】");
        this.id = id;
    }

    public String getName() {
        System.out.println("【调用getName】");
        return name;
    }

    public void setName(String name) {
        System.out.println("【调用setName】");
        this.name = name;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("【调用@PostConstruct】");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("【调用afterPropertiesSet】");
    }

    public void initMethod() {
        System.out.println("【调用initMethod】");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("【调用@PreDestroy】");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("【调用destroy】");
    }

    public void afterDestroy() {
        System.out.println("【调用afterDestroy】");
    }
}
