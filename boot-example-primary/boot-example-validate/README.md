# 一、添加依赖
```
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
</dependencies>
```
# 二、编写实体类
```
@Data
public class User {

    @NotNull(message = "{id.not.empty}")
    private Integer id;

    @NotEmpty(message = "{name.not.empty}")
    private String name;

    @NotEmpty(message = "{email.not.empty}")
    @Email(message = "{email.invalid}")
    private String email;
}
```

# 三、编写控制器
```
@RestController
@RequestMapping("/validate/users")
public class UserController {

    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;

    @RequestMapping("/")
    public String index(@Valid User user, BindingResult bindingResult) {
        List<FieldError> list = bindingResult.getFieldErrors();
        List<String> messageList = new ArrayList<>();
        for (FieldError fieldError : list) {
            messageList.add(fieldError.getDefaultMessage());
        }
        return messageList.toString();
    }
}
```

# 四、配置MessageSource
```
@Configuration
public class ValidateConfigure {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasenames("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}
```

# 五、编写启动类
```
@SpringBootApplication
public class ValidateApplication {

    public static void main(String[] args) {
        SpringApplication.run(ValidateApplication.class, args);
    }
}
```

# 六、创建配置资源文件
在resource目录下面分别创建中文和英文配置文件
1. messages_en_US.properties
```
id.not.empty=id can not be empty
name.not.empty=name can not be empty
email.not.empty=email can not be empty
email.invalid=please provided a valid email address
```
2. messages_zh_CN.properties
```
id.not.empty=编号不能为空
name.not.empty=姓名不能为空
email.not.empty=邮箱不能为空
email.invalid=请提供合法的邮箱地址
```
# 七、测试
1. 访问http://localhost:8080/validate/users/
```
[邮箱不能为空, 编号不能为空, 姓名不能为空]
```
2. 访问http://localhost:8080/validate/users/并且在header中设置accept-language为en-US
```
[name can not be empty, email can not be empty, id can not be empty]
```

# 八、原理分析
1. 调用AbstractMessageSource中的如下方法，按照顺序
    1. getMessage(String code, @Nullable Object[] args, Locale locale)
    2. getMessageInternal()
2. 调用ReloadableResourceBundleMessageSource中的如下方法，按照顺序
    1. resolveCodeWithoutArguments()
    2. getMergedProperties()
    3. calculateAllFilenames()
    4. calculateFilenamesForLocale(String basename, Locale locale)，该方法特意给出了参数信息，会根据我们给定的basename来生成fileName，此处我们的basename为message，我们所在的区域是中国，对应zh_CN，那么生成的文件就存在如下情况：message_zh.properties、message_zh_CN.properties两种，所以我们的resource下面必须要有着两个文件
3. AcceptHeaderLocaleResolver中的方法如下
    1. resolveLocale()该方法会从header中获取Accept-Language的值，来生成对应的Locale

# 九、github代码
[Spring Boot国际化验证](https://github.com/a601942905git/boot-example/tree/master/boot-example-validate)