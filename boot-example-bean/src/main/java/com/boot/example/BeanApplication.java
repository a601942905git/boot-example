package com.boot.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * com.boot.example.BeanApplication
 *
 * @author lipeng
 * @dateTime 2018/12/14 上午10:23
 */
@SpringBootApplication
public class BeanApplication {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(BeanApplication.class, args);
        getAllBeans();
    }

    public static void getAllBeans() {
        int totalBeanCount = applicationContext.getBeanDefinitionCount();
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        System.out.println("【IOC容器中bean的总数】：" + totalBeanCount);

        for (String beanName : beanNames) {
            System.out.println("【IOC容器中bean的名称】：" + beanName);
        }
    }
}
