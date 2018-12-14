# 查看Spring管理的bean

# 方式一、通过Application
```java
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
```

# 方式二、通过暴露的端点
集成监控，访问监控端点
http://localhost:8080/actuator/beans

# 参考wenxian 
[How to Get All Spring-Managed Beans?](https://www.baeldung.com/spring-show-all-beans)