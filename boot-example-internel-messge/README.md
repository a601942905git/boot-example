# 一、添加依赖
```
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

# 二、编写配置类
```
@Configuration
public class InternelMessageConfigure implements WebMvcConfigurer {

    /**
     * 设置国际化资源文件的basename为message
     * 并且设置编码为UTF-8
     * @return
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasenames("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * 将SessionLocaleResolver设置为本地LocaleResolver
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        // 默认语言
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    /**
     * 设置请求参数名为lang
     * @return
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        // 参数名
        lci.setParamName("lang");
        return lci;
    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
```

# 三、编写控制器
```
@Controller
public class InternelMessageController {

    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/")
    public String index() {
        Locale locale = LocaleContextHolder.getLocale();
        System.out.println(messageSource.getMessage("say", null, locale));
        return "index";
    }
}
```

# 四、编写启动类
```
@SpringBootApplication
public class InternelMessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(InternelMessageApplication.class, args);
    }
}
```

# 五、编写资源文件
1. messages_en_US.properties
```
say=hello
```
2. messages_zh_CN.properties
```
say=你好
```

# 六、编写页面
```
<!DOCTYPE html>
<html lang="zh" xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="UTF-8">
        <title>Title</title>
    </head>
    <body>
        <a href="/?lang=en_US">English(US)</a>
        <a href="/?lang=zh_CN">简体中文</a>
        <label th:text="#{say}"></label>
    </body>
</html>
```

# 七、注意事项
网上很多教程在配置类中都少了以下代码
```
/**
 * 设置国际化资源文件的basename为message
 * 并且设置编码为UTF-8
 * @return
 */
@Bean
public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource
            = new ReloadableResourceBundleMessageSource();

    messageSource.setBasenames("classpath:messages");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
}
```
如果我们也去掉该部分代码会怎么样？
```
org.springframework.context.NoSuchMessageException: No message found under code 'say' for locale 'en_US'.
	at org.springframework.context.support.DelegatingMessageSource.getMessage(DelegatingMessageSource.java:76) ~[spring-context-5.1.2.RELEASE.jar:5.1.2.RELEASE]
	at com.boot.example.InternelMessageController.index(InternelMessageController.java:26) ~[classes/:na]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_161]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_161]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_161]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[na:1.8.0_161]
	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:215) ~[spring-web-5.1.2.RELEASE.jar:5.1.2.RELEASE]
	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:142) ~[spring-web-5.1.2.RELEASE.jar:5.1.2.RELEASE]
	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:102) ~[spring-webmvc-5.1.2.RELEASE.jar:5.1.2.RELEASE]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:895) ~[spring-webmvc-5.1.2.RELEASE.jar:5.1.2.RELEASE]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:800) ~[spring-webmvc-5.1.2.RELEASE.jar:5.1.2.RELEASE]
	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87) ~[spring-webmvc-5.1.2.RELEASE.jar:5.1.2.RELEASE]
	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1038) ~[spring-webmvc-5.1.2.RELEASE.jar:5.1.2.RELEASE]
	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:942) ~[spring-webmvc-5.1.2.RELEASE.jar:5.1.2.RELEASE]
	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:998) ~[spring-webmvc-5.1.2.RELEASE.jar:5.1.2.RELEASE]
	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:890) ~[spring-webmvc-5.1.2.RELEASE.jar:5.1.2.RELEASE]
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:634) ~[tomcat-embed-core-9.0.12.jar:9.0.12]
	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:875) ~[spring-webmvc-5.1.2.RELEASE.jar:5.1.2.RELEASE]
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:741) ~[tomcat-embed-core-9.0.12.jar:9.0.12]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231) ~[tomcat-embed-core-9.0.12.jar:9.0.12]

```
可以看到我们找不到资源文件的位置，所以该部分配置是必须的，该项目使用的boot版本为2.1.0.RELEASE

# 八、github代码
[Spring Boot信息国际化](https://github.com/a601942905git/boot-example/tree/master/boot-example-internel-messge)