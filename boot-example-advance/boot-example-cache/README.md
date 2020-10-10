# 一、JetCache
JetCache是一个基于Java的缓存系统封装，提供统一的API和注解来简化缓存的使用。 JetCache提供了比SpringCache更加强大的注解，可以原生的支持TTL、两级缓存、分布式自动刷新，还提供了Cache接口用于手工缓存操作。 当前有四个实现，RedisCache、TairCache（此部分未在github开源）、CaffeineCache(in memory)和一个简易的LinkedHashMapCache(in memory)，要添加新的实现也是非常简单的

全部特性:

1. 通过统一的API访问Cache系统
2. 通过注解实现声明式的方法缓存，支持TTL和两级缓存
3. 通过注解创建并配置Cache实例
4. 针对所有Cache实例和方法缓存的自动统计
5. Key的生成策略和Value的序列化策略是可以配置的
6. 分布式缓存自动刷新，分布式锁 (2.2+)
7. 异步Cache API (2.2+，使用Redis的lettuce客户端时)
8. Spring Boot支持

# 二、示例(Spring Boot)
1. 添加pom依赖
```
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.16.10</version>
</dependency>

<dependency>
    <groupId>com.alicp.jetcache</groupId>
    <artifactId>jetcache-starter-redis</artifactId>
    <version>2.5.9</version>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
2. 配置
```
jetcache:
  statIntervalMinutes: 15
  areaInCacheName: false
  local:
    default:
      type: caffeine
      keyConvertor: fastjson
  remote:
    default:
      type: redis
      keyConvertor: fastjson
      valueEncoder: java
      valueDecoder: java
      poolConfig:
        minIdle: 5
        maxIdle: 20
        maxTotal: 50
      host: 127.0.0.1
      port: 6379
      password: redis-password
```
3. 启动类
```
@SpringBootApplication
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "com.boot.example")
public class JetCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(JetCacheApplication.class, args);
    }
}
```
4. 控制器
```
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/{id}")
    public User getUser(@PathVariable("id") Integer id) {
        return userService.getUser(id);
    }

    @RequestMapping("/getUserFromMethodCache")
    public User getUserFromMethodCache() {
        return userService.getUserFromMethodCache(10006);
    }

    @RequestMapping("/delete")
    public void deleteUser() {
        userService.remove(10006);
    }

    @RequestMapping("/update")
    public void update() {
        User user = User.builder()
                .id(10006)
                .name("update cache")
                .build();
        userService.updateUser(user);
    }

    @RequestMapping("/getUserFromRefreshCache")
    public User getUserFromRefreshCache() {
        return userService.getUserFromRefreshCache();
    }
}
```
5. 业务层接口
```
public interface UserService {

    @Cached(name = "UserService.getUserFromMethodCache", expire = 100, cacheType = CacheType.BOTH, key = "#id")
    User getUserFromMethodCache(Integer id);

    User getUser(Integer id);

    @CacheUpdate(name = "UserService.getUserFromMethodCache", key = "#user.id", value = "#user")
    void updateUser(User user);

    @CacheInvalidate(name = "UserService.getUserFromMethodCache", key = "#id")
    void remove(Integer id);

    @Cached(name = "UserService.getUserFromRefreshCache", expire = 10, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 2, stopRefreshAfterLastAccess = 100)
    User getUserFromRefreshCache();
}
```
6. 业务实现
```
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @CreateCache(name = "UserService.getUser", expire = 100, cacheType = CacheType.BOTH)
    private Cache<Integer, User> userCache;

    @Override
    public User getUser(Integer id) {
        User user = userCache.get(id);
        if (Objects.isNull(user)) {
            log.info("<<<缓存中不存在>>>");
            User user1 = User.builder()
                    .id(10001)
                    .name("测试")
                    .build();
            userCache.put(id, user1);
            return user1;
        }
        log.info("<<<从缓存中获取>>>");
        return user;
    }

    @Override
    public User getUserFromMethodCache(Integer id) {
        return User.builder()
                .id(10002)
                .name("test cache method")
                .build();
    }

    @Override
    public User getUserFromRefreshCache() {
        return User.builder()
                .id(10003)
                .name("test refersh cache")
                .build();
    }

    @Override
    public void updateUser(User user) {
        System.out.println("修改用户信息");
    }

    @Override
    public void remove(Integer id) {
        System.out.println("删除用户信息");
    }
}
```
7. 实体类
```
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {

    private Integer id;

    private String name;
}

```

# 三、讲解
@EnableCreateCacheAnnotation注解用来激活@CreateCache注解

@EnableMethodCache注解用来激活@Cached注解

接口层@Cached注解中的key和value有使用到#id这样的SPEL表达式，那么需要添加如下配置
```
<plugins>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.1</version>
        <configuration>
            <compilerArgument>-parameters</compilerArgument>
            <encoding>UTF-8</encoding>
            <source>1.8</source>
            <target>1.8</target>
        </configuration>
    </plugin>
</plugins>
```

# 四、缓存更新问题
先更新数据库，然后再使缓存失效，没有提供重试机制
[请问jetcache @CacheUpdate 更新缓存的策略是什么？](https://github.com/alibaba/jetcache/issues/115)

# 五、二级缓存更新问题
服务是一个集群，存在A、B两个服务，A服务在更新缓存的时候只会更新本机缓存和远程redis缓存，并不会去更新服务B的本地缓存。

# 六、缓存刷新问题
假如设置的缓存刷新时间间隔为10s，刷新数据需要耗时5s，那么在10~15之间访问该接口返回的还是旧数据

# 七、个人疑惑
1. 关于@CacheUpdate、@CacheRefresh、@CacheInvalidate的原理