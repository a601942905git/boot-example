# 一、添加依赖
```
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```
# 二、build下面添加resource
```
<build>
    <resources>
        <resource>
            <directory>${basedir}/src/main/resources</directory>
            <filtering>true</filtering>
        </resource>
    </resources>
</build>
```
# 三、编写配置文件
```
project.name=@project.name@
project.version=@project.version@
project.artifactId=@project.artifactId@
```
# 四、编写启动类
```
@SpringBootApplication
public class AutomaticPropertiesExpansionApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext =
                SpringApplication.run(AutomaticPropertiesExpansionApplication.class, args);

        Environment environment = applicationContext.getEnvironment();

        System.out.println("项目名称：" + environment.getProperty("project.name"));
        System.out.println("项目版本：" + environment.getProperty("project.version"));
        System.out.println("项目artifactId：" + environment.getProperty("project.artifactId"));
    }
}
```
# 五、打印结果：
```
项目名称：boot-example-automatic-properties-expansion
项目版本：1.0-SNAPSHOT
项目artifactId：boot-example-automatic-properties-expansion
```

# 六、github代码
[Spring Boot自定义属性扩展](https://github.com/a601942905git/boot-example/tree/master/boot-example-automatic-properties-expansion)

# 七、参考文献
[Automatic Property Expansion with Spring Boot](https://www.baeldung.com/spring-boot-auto-property-expansion)