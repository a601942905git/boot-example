默认情况下我们请求出现错误会走BasicErrorController的errorHtml方法，跳转到Spring Boot自带的错误页面。下面我们来自定义属于我们自己的错误页面，包括404、500这样的页面。

我们需要做的就是要重写ErrorController

# 一、添加依赖
```
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
</dependencies>
```

# 二、编写控制器
```
@RestController
@RequestMapping("/custom")
public class CustomerController {

    @RequestMapping("/")
    public void index() {
        throw new RuntimeException("抛出异常。。。。。。");
    }
}
```

# 三、编写自定义错误控制器
```
@Controller
public class CustomErrorPageController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping("/error")
    public String error(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error-404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error-500";
            }
        }
        return "error";
    }
}
```

# 四、编写启动类
```
@SpringBootApplication
public class CustomErrorPageApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomErrorPageApplication.class, args);
    }
}
```

# 五、编写错误页面
1. 错误页面
```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <label>custom error page</label>
</body>
</html>
```
2. 404页面
```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <label>custom error page 404</label>
</body>
</html>
```
3. 500页面
```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <label>custom error page 500</label>
</body>
</html>
```

# 六、github代码
[Spring Boot自定义错误页面](https://github.com/a601942905git/boot-example/tree/master/boot-example-custom-errorpage)