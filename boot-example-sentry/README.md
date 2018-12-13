# 一、Sentry介绍
Open-source error tracking that helps developers monitor and fix crashes in real time. Iterate continuously. Boost efficiency. Improve user experience.

# 二、创建项目
1. 在sentry网站中创建一个项目
2. 在项目的setting--->Client Keys--->复制DNS

# 三、新建Spring Boot项目
1. 添加依赖
```
<dependencies>
    <dependency>
        <groupId>io.sentry</groupId>
        <artifactId>sentry-spring</artifactId>
        <version>1.7.5</version>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```
2. 添加启动类
```
@Controller
@SpringBootApplication
public class SentryApplication {

    /**
     * Register a HandlerExceptionResolver that sends all exceptions to Sentry
     *     and then defers all handling to the other HandlerExceptionResolvers.
     *     You should only register this is you are not using a logging integration,
     *     otherwise you may double report exceptions.
     * @return
     */
    @Bean
    public HandlerExceptionResolver sentryExceptionResolver() {
        return new io.sentry.spring.SentryExceptionResolver();
    }

    /**
     * Register a ServletContextInitializer that installs the SentryServletRequestListener
     *     so that Sentry events contain HTTP request information.
     *     This should only be necessary in Spring Boot applications. "Classic" Spring
     *     should automatically load the `io.sentry.servlet.SentryServletContainerInitializer`.
     * @return
     */
    @Bean
    public ServletContextInitializer sentryServletContextInitializer() {
        return new io.sentry.spring.SentryServletContextInitializer();
    }

    @RequestMapping("/")
    @ResponseBody
    String home() {
        int x = 1 / 0;

        return "Hello World!";
    }

    public static void main(String[] args) {
        SpringApplication.run(SentryApplication.class, args);
    }
}
```
3. 在resources中创建sentry.properties，名字一定要一样
```
dsn=在项目的setting--->Client Keys--->复制DNS
stacktrace.app.packages=com.boot.example
```
4. 访问http://localhost:8080/
5. 效果如下
![](https://mdsystem.oss-cn-hangzhou.aliyuncs.com/blog/1543905907292.jpg)