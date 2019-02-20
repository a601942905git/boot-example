# 事务介绍
使用事务的目的就是让我们的业务能保证原子性操作，要么同时成功，要么同时失败，从而避免部分成功、部分失败的，出现脏数据的情况。

# 事务特性
1. 原子性
2. 一致性
3. 隔离性
4. 持久性


# 事务隔离级别
1. 未提交度
2. 已提交度
3. 重复读
4. 可串行化

# 事务传播特性
1. REQUIRED：如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。
2. SUPPORTS：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
3. MANDATORY：如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。
4. REQUIRES_NEW：创建一个新的事务，如果当前存在事务，则把当前事务挂起。
5. NOT_SUPPORTED：以非事务方式运行，如果当前存在事务，则把当前事务挂起。
6. NEVER：以非事务方式运行，如果当前存在事务，则抛出异常。
7. NESTED：如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于REQUIRED

# 示例代码
1. pom
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

    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>

    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
    </dependency>
</dependencies>
```
2. 配置文件
```
# DataSource
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false
spring.datasource.username=root
spring.datasource.password=root

# Hikari will use the above plus the following to setup connection pooling
spring.datasource.type=com.zaxxer.hikari.HikariDataSource
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.maximum-pool-size=15
spring.datasource.hikari.auto-commit=true
spring.datasource.hikari.idle-timeout=30000
spring.datasource.hikari.pool-name=DatebookHikariCP
spring.datasource.hikari.max-lifetime=1800000
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.connection-test-query=SELECT 1

# Spring Boot Mybatis
mybatis.mapperLocations = classpath*:mapper/**/*.xml
mybatis.configuration.mapUnderscoreToCamelCase=true
```
3. 实体类
```
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Student {

    private Integer id;

    private String name;

    private Integer age;
}
```
4. mapper
```
@Mapper
public interface StudentMapper {

    int saveStudent(Student student);
}
```
5. service
```
@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public int saveStudent(Student student) {
        int result = this.studentMapper.saveStudent(student);
        return result;
    }
}
```
6. controller
```
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/")
    public Integer addStudent(@RequestBody Student student) {
        return this.studentService.saveStudent(student);
    }
}
```
7. 启动类
```
@SpringBootApplication
public class BootTransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootTransactionApplication.class, args);
    }
}
```
8. xml
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.boot.example.student.StudentMapper">

    <sql id="BASE_SELECT_COLUMN">
        id,
        name,
        age
    </sql>

    <insert id="saveStudent" parameterType="com.boot.example.student.Student">
        INSERT INTO student VALUES(#{id}, #{name}, #{age})
    </insert>
</mapper>
```
9. 测试
```
http://localhost:8080/students/

{
	"id":10008,
	"name":"test88",
	"age":88
}
```
在数据库中可以看到刚才新增的数据

# 验证事务
修改service代码
```
@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Transactional(rollbackFor = Exception.class)
    public int saveStudent(Student student) {
        int result = this.studentMapper.saveStudent(student);
        System.out.println(1 / 0);
        return result;
    }
}
```
再次通过接口新增用户信息，程序报错，数据库中未出现新增的数据，说明事务已经起作用了。


# 验证Propagation.REQUIRED事务的传播性
修改service代码
```
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public int saveCommon(Student student, User user) {
    int result = studentMapper.saveStudent(student);
    userService.saveUser(user);
    System.out.println(1 / 0);
    return result;
}
```
如上业务代码调用了UserService的方法，UserService的saveUser()方法也指明了事务

按照之前的说明Propagation.REQUIRED含义是，如果当前存在事务，则加入该事务，如果当前没有事务，则创建新的事务

首先验证如果当前存在事务，则加入当前事务。

==注意点：== 重点就是如果当前事务方法调用其它带有事务的方法，所有的业务逻辑都处于当前方法的事务中

在StudentController中新增一个方法
```
@PostMapping("/common")
public Integer saveCommon(@RequestBody Student student) {
    User user = new User();
    BeanUtils.copyProperties(student, user);
    return this.studentService.saveCommon(student, user);
}
```
调用当前控制器方法来验证，验证的结果就是，student表没有插入数据，user表中也没有插入数据，符合之前的理论观点。

接下来验证，如果当前方法没有事务，则创建新的事务，修改UserService.saveUser()方法
```
@Transactional(rollbackFor = Exception.class)
public int saveUser(User user) {
    System.out.println(1 / 0);
    return userMapper.saveUser(user);
}
```
修改StudentService.saveStudent()方法
```
public int saveCommon(Student student, User user) {
    int result = studentMapper.saveStudent(student);
    userService.saveUser(user);
    return result;
}
```

把saveStudent()方法的事务去掉，UserService的saveUser()方法还是有事务的，那么当前方法不存在事务，那么 userService.saveUser(user);这段代码会创建新的事务

再次调用控制器我们可以看到，student表中新增了数据，user表中没有新增数据

# 验证Propagation.REQUIRES_NEW事务的传播性
创建新的事务，如果当前方法存在事务，那么当前事务挂起。

studentService.saveStudent()方法代码如下：
```
@Transactional(rollbackFor = Exception.class)
public int saveCommon(Student student, User user) {
    int result = studentMapper.saveStudent(student);
    userService.saveUser(user);
    System.out.println(1 / 0);
    return result;
}
```

userService.saveUser()方法代码如下：
```
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
public int saveUser(User user) {
    return userMapper.saveUser(user);
}
```

调用控制器查看结果：

student表中没有新增数据，user表中新增了一条数据，结果也符合之前的理论观点，由于userService.saveUser()方法事务的传播性指定的是：创建新的事务，当前方法事务挂起。所以在执行studentService.saveStudent()方法的时候，虽然最终抛出了异常，但是调用的userService.saveUser()方法是在一个新的事务中，执行完成就提交了，所以user表中就插入了数据。之后抛出异常，跟之前的事务没有任何关系，只会造成当前方法事务回滚。


# 事务特殊场景
1. 同一个service中，非事务方法调用事务方法，会发现事务失效
2. 当调用既包含事务又包含同步的方法，多线程情况下会出现线程不安全

场景一和场景二相关代码在TestController和TestService中

# 场景一
通过被代理对象调用方法而不是通过this(对象本身)调用

# 场景二
将同步范围扩大，将事务也包裹起来
