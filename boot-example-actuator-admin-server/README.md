# Spring Boot Admin Server监控

## 一、服务端
1. 创建maven项目
2. 引入依赖
```
<dependency>
    <groupId>de.codecentric</groupId>
    <artifactId>spring-boot-admin-starter-server</artifactId>
    <version>2.1.0</version>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>2.1.0</version>
</dependency>
```
3. 创建启动类
```
@Configuration
@EnableAutoConfiguration
@EnableAdminServer
@Slf4j
public class SpringBootAdminServerApplication {

    public static void main(String[] args) throws UnknownHostException {
        Environment environment = SpringApplication.run(SpringBootAdminServerApplication.class, args).getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        log.info("=================================");
        log.info("      项目名称:" + environment.getProperty("spring.application.name"));
        log.info("      项目访问地址:" + ip + ":" + environment.getProperty("server.port"));
        log.info("=================================");
    }
}
```
4. 访问项目
http://192.168.0.5:8880/#/applications
5. 效果
![](https://mdsystem.oss-cn-hangzhou.aliyuncs.com/blog/E63B201D-3BB1-48F3-98B9-E779FFCD39BB.png)

## 二、客户端
1. 添加依赖
```
<dependency>
    <groupId>de.codecentric</groupId>
    <artifactId>spring-boot-admin-starter-client</artifactId>
    <version>2.1.0</version>
</dependency>
```
2. 配置Spring Boot Admin Server地址
```
spring.boot.admin.client.url=http://localhost:8880
```
3. 暴露监控端点
```
management.endpoints.web.exposure.include=*
```
4. 启动客户端
![](https://mdsystem.oss-cn-hangzhou.aliyuncs.com/blog/E74FD11D-1673-45AA-AC9A-6ED2CA5D4536.png)