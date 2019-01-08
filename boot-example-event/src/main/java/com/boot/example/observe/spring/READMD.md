# 使用Spring事件模型来实现发布/订阅

# 定义事件
```java
public class RegisterEvent extends ApplicationEvent implements Serializable {

    private static final long serialVersionUID = -4753665584529412874L;

    public RegisterEvent(User user) {
        super(user);
    }
}
```

# 定义事件监听者
```java
@Component
public class EmailListener implements SmartApplicationListener {

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == RegisterEvent.class;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        User user = (User) event.getSource();
        System.out.println("【用户" + user.getName() + "注册成功】：发送短信");
    }
}
```

```java
@Component
public class PointListener implements SmartApplicationListener {

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == RegisterEvent.class;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        User user = (User) event.getSource();
        System.out.println("【用户" + user.getName() + "注册成功】：发送短信");
    }
}
```

```java
@Component
public class SMSListener implements SmartApplicationListener {

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == RegisterEvent.class;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        User user = (User) event.getSource();
        System.out.println("【用户" + user.getName() + "注册成功】：发送短信");
    }
}
```

# 定义事件发布者
```java
@Component
public class RegisterEventPublisher implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void publishRegisterEvent(User user) {
        applicationContext.publishEvent(new RegisterEvent(user));
    }
}
```

# 定义实体类
```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    private Integer id;

    private String name;
}
```

# 定义业务类
```java
@Service
public class UserService {

    @Autowired
    private RegisterEventPublisher registerEventPublisher;

    public User register() {
        User user = User.builder().id(10001).name("张三").build();
        System.out.println("【用户" + user.getName() + "注册成功！】");
        registerEventPublisher.publishRegisterEvent(user);
        return user;
    }
}
```

# 定义启动类
```java
@SpringBootApplication
public class SpringEventApplication {

    private static UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        SpringEventApplication.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringEventApplication.class, args);
        System.out.println(userService.register());
    }
}
```

# 查看结果
```
【用户张三注册成功！】
【用户张三注册成功】：发送短信
【用户张三注册成功】：发送短信
【用户张三注册成功】：发送短信
User(id=10001, name=张三)
```