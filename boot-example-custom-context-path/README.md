# 自定义context-path
Spring Boot项目默认情况下访问路径为：http://localhost:8080，此时的context-path为"/"，当然我们也可以自定义context-path

本文采用的是通过配置文件的方式来自定义context-path
```
server.servlet.context-path=/demo
```

启动类
```
@SpringBootApplication
public class CustomContextPathApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomContextPathApplication.class, args);
    }
}
```

控制器
```
@RestController
public class CustomerContextPathController {

    @RequestMapping("/custom")
    public List<String> index(HttpServletRequest request) {
        List<String> stringList = new ArrayList<>();
        stringList.add("custom context path：" + request.getContextPath());
        stringList.add("custom request url：" + request.getRequestURL());
        stringList.add("custom request uri：" + request.getRequestURI());
        return stringList;
    }
}
```

访问http://localhost:8080/demo/custom，结果如下：
```
["custom context path：/demo","custom request url：http://localhost:8080/demo/custom","custom request uri：/demo/custom"]
```