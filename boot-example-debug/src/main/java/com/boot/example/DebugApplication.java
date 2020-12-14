package com.boot.example;

import com.boot.example.entity.User;
import com.boot.example.event.RegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * com.boot.example.DebugApplicaiton
 *
 * @author lipeng
 * @date 2020/7/2 2:30 PM
 */
@SpringBootApplication
@Slf4j
public class DebugApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(DebugApplication.class, args);
        publishEvent(applicationContext);
        ConfigurableBeanFactory beanFactory = applicationContext.getBeanFactory();
        // 解析内嵌值
        log.info("===>" + beanFactory.resolveEmbeddedValue("${inject.value}"));
        // 获取FactoryBean
        log.info("===>" + beanFactory.getBean("&rpcFactoryBean"));
        // 获取getObject()返回的bean
        log.info("===>" + beanFactory.getBean("rpcFactoryBean"));
        log.info("container singleton count===>" + beanFactory.getSingletonCount());
        // Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(System.out::println);
    }

    public static void publishEvent(ApplicationContext applicationContext) {
        User user = User.builder()
                .id(10001)
                .name("新注册用户01")
                .build();
        applicationContext.publishEvent(new RegisterEvent(user));
    }

    @Bean
    public Person person() {
        return new Person();
    }
}
