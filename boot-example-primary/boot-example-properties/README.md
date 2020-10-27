# @EnableConfigurationProperties
该注解用来提供对@ConfigurationProperties注解的支持

# 使用示例
编写属性类
```java
@ConfigurationProperties(prefix = "student")
public class StudentProperties {

    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```
编写自动配置类
```java
@Configuration
@EnableConfigurationProperties(StudentProperties.class)
public class StudentAutoConfigure {
}
```
编写控制器
```java
@RestController
public class StudentController {

    @Autowired
    private StudentProperties studentProperties;

    @RequestMapping("/student")
    public StudentProperties index() {
        return studentProperties;
    }
}
```
编写启动类
```java
@SpringBootApplication
public class PropertiesApplication {

    public static void main(String[] args) {
        SpringApplication.run(PropertiesApplication.class, args);
    }
}
```

# 复杂属性的注入
编写实体类
```java
@ConfigurationProperties(prefix="complex")
public class ComplexStudentProperties {

    private List<StudentProperties> list;

    private Map<String, StudentProperties> map;

    public List<StudentProperties> getList() {
        return list;
    }

    public void setList(List<StudentProperties> list) {
        this.list = list;
    }

    public Map<String, StudentProperties> getMap() {
        return map;
    }

    public void setMap(Map<String, StudentProperties> map) {
        this.map = map;
    }
}
```
编写配置
```yaml
complex:
  list:
    - id: 10002
      name: list1
    - id: 10003
      name: list2

  map:
    key1:
      id: 10004
      name: map1
    key2:
      id: 10005
      name: map2
```
编写控制器
```java
@RestController
public class ComplexController {

    @Autowired
    private ComplexStudentProperties complexStudentProperties;

    @Autowired
    private ComplexAutoConfigure complexAutoConfigure;

    @RequestMapping("/complex")
    public ComplexStudentProperties index() {
        return complexStudentProperties;
    }
}
```


# 属性注入Bean
当我们需要将配置文件中的属性注入到一个Bean时，如果使用@Value注解的话就会显得很臃肿，一种好的解决方法就是使用@ConfigurationProperties注解，下面来演示如何使用该注解

# 示例
接下来会拿一个App这样一个对象来举例说明,先来看一下配置文件信息
```
app:
  id: 20001
  name: shein
  version: 2.1.8
  publishDate: 2018-10-11
  publishDateTime: 2018-10-11 12:30:36
```
前面的3个属性都很常规，属于常用类型，最后两个使用了LocalDate和LocalDateTime来接收，什么都先不管，开工

定义一个接收属性的实体类
```
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private Integer id;

    private String name;

    private String version;

    private LocalDate publishDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishDateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public LocalDateTime getPublishDateTime() {
        return publishDateTime;
    }

    public void setPublishDateTime(LocalDateTime publishDateTime) {
        this.publishDateTime = publishDateTime;
    }
}
```
可以看到该实体类使用了我们上面提到的@ConfigurationProperties注解，并且指定了前缀为app，这样就会把id、name、versin等属性自动注入到我们的实体类

当然光是上面这样是远远不够的，下面我们来启用属性配置
```
@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class AppAutoConfigure {
}
```
经过如上两步，就大功告成了，接下来定义一个控制器便于查看效果
```
@RestController
public class AppController {

    @Autowired
    private AppProperties appProperties;

    @RequestMapping("/convert")
    public AppProperties index() {
        return appProperties;
    }
}
```
启动程序，会发现我们的程序压根都启动不了，查看控制台可以看到如下输出信息
```
Description:

Failed to bind properties under 'app.publish-date' to java.time.LocalDate:

    Property: app.publishdate
    Value: 2018-10-11
    Origin: class path resource [application.yaml]:24:16
    Reason: failed to convert java.lang.String to java.time.LocalDate

Action:
```
大致的意思就是我们无法转换2个日期对于我们给定的类型

那么好办，我们就自定义转换器来进行转换
```
@Component
@ConfigurationPropertiesBinding
public class LocalDateConvert implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String source) {
        Objects.requireNonNull(source);
        return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
```

```
@Component
@ConfigurationPropertiesBinding
public class LocalDateTimeConvert implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String source) {
        Objects.requireNonNull(source);
        return LocalDateTime.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
```
定义好了2个转换器后，再次启动我们的程序，发现可以了，然后访问 http://localhost:8080/convert就可以看到结果了
```
{"id":20001,"name":"shein","version":"2.1.8","publishDate":"2018-10-11","publishDateTime":"2018-10-11 12:30:36"}
```