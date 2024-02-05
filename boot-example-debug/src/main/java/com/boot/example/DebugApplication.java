package com.boot.example;

import com.boot.example.entity.User;
import com.boot.example.event.RegisterEvent;
import com.boot.example.factory.Tiger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
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

    private static Tiger tiger;

    @Autowired
    public void setPerson(Tiger tiger) {
        DebugApplication.tiger = tiger;
    }

    public static void main(String[] args) {
        // 设置应用启动的缓存大小，用于记录启动过程中耗时情况
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(DebugApplication.class)
                .applicationStartup(new BufferingApplicationStartup(20480))
                .run(args);
        // 发布事件
        publishEvent(applicationContext);
        ConfigurableBeanFactory beanFactory = applicationContext.getBeanFactory();
        // 解析内嵌值
        log.info("===>" + beanFactory.resolveEmbeddedValue("${inject.value}"));
        getFactoryBean(beanFactory);
        proxyFactoryBeanTest();
        // 获取getObject()返回的bean
        log.info("container singleton count===>" + beanFactory.getSingletonCount());
        // Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(System.out::println);
    }

    private static void getFactoryBean(ConfigurableBeanFactory beanFactory) {
        // 从IOC容器中获取名称为"rpcFactoryBean"对象，也就是RpcFactoryBean，
        // 由于名称不是以"&"开头，会从factoryBeanObjectCache中获取名称为"rpcFactoryBean"对象，
        // 也就是通过RpcFactoryBean.getObject()方法创建出来的对象
        log.info("get by bean name rpcFactoryBean===>" + beanFactory.getBean("userFactoryBean"));
        // 从IOC容器中获取名称为"rpcFactoryBean"对象，也就是RpcFactoryBean，
        // 由于名称是以"&"开头，直接返回对象
        log.info("get by bean name &rpcFactoryBean===>" +
                beanFactory.getBean(BeanFactory.FACTORY_BEAN_PREFIX + "userFactoryBean"));
    }

    private static void proxyFactoryBeanTest() {
        tiger.hello();
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
