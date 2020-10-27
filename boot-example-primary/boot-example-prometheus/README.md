# 一、添加项目依赖
```
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>



    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>

    <!-- Micrometer Prometheus registry  -->
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-registry-prometheus</artifactId>
    </dependency>
</dependencies>
```

# 二、暴露Spring Boot项目所有的监控端点
在application.properties中添加如下配置
```
spring.jackson.serialization.indent-output=true
management.endpoints.web.exposure.include=*
```

# 三、编写控制器
```
@RestController
public class HelloController {

    private Counter helloCounter = Metrics.counter("request_total", "uri", "/hello");

    private Counter hiCounter = Metrics.counter("request_total", "uri", "/hi");

    @RequestMapping("/hello")
    public void hello() throws InterruptedException {
        while (true) {
            TimeUnit.SECONDS.sleep(5);
            helloCounter.increment();
        }
    }

    @RequestMapping("/hi")
    public void hi() throws InterruptedException {
        while (true) {
            TimeUnit.SECONDS.sleep(10);
            hiCounter.increment();
        }
    }
}
```
定义了2个计数器，通过uri来进行区分

# 四、编写启动类
```
@SpringBootApplication
public class PrometheusApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrometheusApplication.class, args);
    }
}
```

# 五、启动项目
访问 http://localhost:8080/hello 和 http://localhost:8080/hi

# 六、访问Prometheus服务
http://localhost:9090
![](https://mdsystem.oss-cn-hangzhou.aliyuncs.com/blog/1545722280780.jpg)
到此Prometheus可以监控我们的Spring Boot应用了