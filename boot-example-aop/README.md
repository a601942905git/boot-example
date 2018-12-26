# Aop
切面的目的对方面进行切入，在方法执行前，方法执行后、方法异常做一些特殊的处理，比如我们想记录请求耗时、记录请求日志、加入缓存等等都可以采用切面的方式。

# Aop要素
1. 定义切入点
2. 定义切面

# 切入点
切入点就是在什么地方进行切入，比如我们可以在控制器方法这个点切入，也可以在业务层方法这个点切入

# 切面
定义好切入点之后，我们知道要切入那个层面的方法，比如是控制器层面。但是方法又可以分为以下几种情况：
1. 方法执行前
2. 方法执行返回
3. 方法执行异常
我们需要选择面来进行切入

# Spring Boot Aop实战
1. 添加依赖
```
<dependencies>

    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-aop</artifactId>
    </dependency>
</dependencies>
```
2. 开启并配置aop
```
# AOP
# Add @EnableAspectJAutoProxy.
spring.aop.auto=true
# Whether subclass-based (CGLIB) proxies are to be created (true), as opposed to standard Java interface-based proxies (false).
spring.aop.proxy-target-class=true
```
3. 编写切面类
```
@Component
@Aspect
@Slf4j
public class SystemLogAspect {

    /**
     * 定义切入点
     */
    @Pointcut("execution(* com.boot.example.controller.*.*(..))")
    public void SystemLogAspect() {
    }

    /**
     * 方法执行之前通知
     *
     * @param joinPoint
     */
    @Before("SystemLogAspect()")
    public void doBefore(JoinPoint joinPoint) {
    }

    /**
     * 方法返回结果后通知
     */
    @AfterReturning("SystemLogAspect()")
    public void doAfterReturning() {

    }

    /**
     * 方法环绕通知
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Around("SystemLogAspect()")
    public void doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 记录请求开始时间
        Long startTime = Clock.systemDefaultZone().millis();
        Signature signature = joinPoint.getSignature();
        HttpServletRequest request = WebContextUtils.getRequest();
        log.info("==================请求开始=====================");
        log.info("Request IP:{}", request.getRemoteAddr());
        log.info("Request URL:{}", request.getRequestURL());
        log.info("Request Method:{}", request.getMethod());
        log.info("Request Class Method:{}", signature.getDeclaringTypeName() + "#" + signature.getName());
        log.info("Request Method Args:{}", Arrays.toString(joinPoint.getArgs()));

        Object result = joinPoint.proceed();
        log.info("方法执行结果:{}", result);

        Long diffSystemMillis = Clock.systemDefaultZone().millis() - startTime;
        log.info("Request Execute Time:{}", diffSystemMillis);
        log.info("==================请求结束=====================\n");
    }

    /**
     * 方法异常通知
     *
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(value = "SystemLogAspect()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        Signature signature = joinPoint.getSignature();
        log.info("Request Class Method:{}",
                signature.getDeclaringTypeName() +
                        "#" + signature.getName() + ",Throw Exception" + ex.getMessage());
    }
}
```
4. 编写控制器
```
@RestController
@RequestMapping("/users")
public class UserController {

    @RequestMapping("/")
    public String listUser() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        return "访问用户列表信息";
    }

    @RequestMapping("/save")
    public String saveUser() {
        return "新增用户信息";
    }

    @RequestMapping("/modify")
    public String modifyUser() {
        return "修改用户信息";
    }

    @RequestMapping("/delete")
    public String deleteUser() {
        throw new RuntimeException("删除用户失败");
    }
}
```
5. 编写启动类
```
@SpringBootApplication
public class AopApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopApplication.class, args);
    }
}
```
6. 工具类
```
public class WebContextUtils {

    public static HttpServletRequest getRequest() {
        return getServletRequestAttributes().getRequest();
    }

    public static HttpServletResponse getResponse() {
        return getServletRequestAttributes().getResponse();
    }

    private static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }
}
```
7. 访问 http://localhost:8080/users/ 效果如下：
```
2018-12-26 10:32:23.744  INFO 26902 --- [nio-8080-exec-1] com.boot.example.SystemLogAspect         : ==================请求开始=====================
2018-12-26 10:32:23.744  INFO 26902 --- [nio-8080-exec-1] com.boot.example.SystemLogAspect         : Request IP:0:0:0:0:0:0:0:1
2018-12-26 10:32:23.744  INFO 26902 --- [nio-8080-exec-1] com.boot.example.SystemLogAspect         : Request URL:http://localhost:8080/users/
2018-12-26 10:32:23.745  INFO 26902 --- [nio-8080-exec-1] com.boot.example.SystemLogAspect         : Request Method:GET
2018-12-26 10:32:23.745  INFO 26902 --- [nio-8080-exec-1] com.boot.example.SystemLogAspect         : Request Class Method:com.boot.example.controller.UserController#listUser
2018-12-26 10:32:23.745  INFO 26902 --- [nio-8080-exec-1] com.boot.example.SystemLogAspect         : Request Method Args:[]
2018-12-26 10:32:24.752  INFO 26902 --- [nio-8080-exec-1] com.boot.example.SystemLogAspect         : 方法执行结果:访问用户列表信息
2018-12-26 10:32:24.752  INFO 26902 --- [nio-8080-exec-1] com.boot.example.SystemLogAspect         : Request Execute Time:1010
2018-12-26 10:32:24.752  INFO 26902 --- [nio-8080-exec-1] com.boot.example.SystemLogAspect         : ==================请求结束=====================
```