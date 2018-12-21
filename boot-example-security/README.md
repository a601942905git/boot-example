# 一、Security简介
Spring Boot整合了安全验证框架，用来为我们的系统保驾护航

# 二、应用
1. 添加依赖
```
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
</dependencies>
```
2. 编写控制器
```
@RestController
public class WelcomeController {

    @RequestMapping("/")
    public String welcome() {
        return "登录成功，欢迎来到：https://a601942905git.github.io/";
    }
}
```
3. 编写启动类
```
@SpringBootApplication
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }
}
```
以上的代码都是正常的代码，和我们平时使用没什么区别，无非是多了一个spring-boot-starter-security依赖。多了这个依赖就可以为我们的程序保驾护航了，不信，可以访问我们的程序。

在浏览器中输入：http://localhost:8080/login 会发现不能像以前那样愉快的直接访问我们的控制器，需要我们进行登录，那么接下来就输入账号密码来登录
![](https://mdsystem.oss-cn-hangzhou.aliyuncs.com/blog/1544758360698.jpg)

账号密码从哪里来呢？

打开我们程序的控制台会看到如下字符串
```
 Using generated security password: 4d561ade-0d0d-486e-900a-1634afdc2d3a
```

既然有了密码，那账号是什么？控制台也没有，如果了解过Spring Boot Starter原理的，可以猜到一定会有一个属性配置类，来指定默认属性，那么按着猜测的想法，在IDEA中搜索SecurityProperties这个类，往下翻我们可以看到如下代码
```
public static class User {

		/**
		 * Default user name.
		 */
		private String name = "user";

		/**
		 * Password for the default user name.
		 */
		private String password = UUID.randomUUID().toString();
}
```
好了，到此为止我们已经了解到了我们的账号、密码，接下来登录，就可以登录成功了。

==注意点：== 我们通过上面的代码也看到，密码是通过UUID的方式生成的，每次都不一样，每次登录的时候我们都需要翻看控制台的内容，很麻烦。同样我们也知道，Spring Boot自动配置的属性，我们都是可以覆盖的。

在application.properties中添加如下配置：
```
spring.security.user.name=root
spring.security.user.password=root
```
这样我们就可以通过root、root登录了

# 三、github代码
[Spring Boot Security入门](https://github.com/a601942905git/boot-example/tree/master/boot-example-security)