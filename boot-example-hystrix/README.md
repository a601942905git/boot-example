# 一、简单使用
1. 定义command
2. 继承HystrixCommand
3. 指定构造，构造函数需要调用父类构造，并且指定groupKey
4. 在重写的run()方法中编写依赖逻辑

```
public class HelloWorldCommand extends HystrixCommand<String> {

    private String name;

    public HelloWorldCommand(String name) {
        // 指定命令组名称
        super(HystrixCommandGroupKey.Factory.asKey("hello-world"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        return "Hello " + name + ",current thread:" + Thread.currentThread().getName();
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        /**
         * 每个命令对象实例只能调用一次，不能重复调用
         */
        HelloWorldCommand helloWorldCommand = new HelloWorldCommand("Synchronous-hystrix");
        // 同步调用，执行run()方法
        String executeResult = helloWorldCommand.execute();
        System.out.println("HelloWorldCommand ExecuteResult:" + executeResult);

        helloWorldCommand = new HelloWorldCommand("Asynchronous-hystrix");
        // 异步调用
        Future<String> future = helloWorldCommand.queue();
        // 同步阻塞获取结果
        String futureResult = future.get(100, TimeUnit.SECONDS);
        System.out.println("HelloWorldCommand QueueResult:" + futureResult);

        System.out.println("Main Thread:" + Thread.currentThread().getName());
    }
}
```

# 二、带有降级逻辑
1. 重写父类的getFallback()方法
```
public class FallBackCommand extends HystrixCommand<String> {

    private String name;

    public FallBackCommand(String name) {
        /**
         * 配置hystrix执行超时时间为500ms
         */
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("fallback"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(500))
        );
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        Thread.sleep(1000);
        return "Hello " + name + ",current thread:" + Thread.currentThread().getName();
    }

    @Override
    protected String getFallback() {
        return "服务超时，对服务进行降级";
    }

    public static void main(String[] args) {
        FallBackCommand fallBackCommand = new FallBackCommand("fallback-hystrix");
        String fallBackResult = fallBackCommand.execute();
        System.out.println("FallBackCommand FallBackResult：" + fallBackResult);
    }
}
```

# 三、模拟信号量隔离
1. 同时启动20个线程并发执行
2. 只有10个可以成功执行，另外10个只能走降级逻辑
```
public class SemaphoreCommand extends HystrixCommand<String> {

    CountDownLatch countDownLatch = new CountDownLatch(20);

    private String name;

    /**
     * 信号量隔离，并发请求数为10
     * @param name
     */
    public SemaphoreCommand(String name) {
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("semaphore"))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter().withExecutionIsolationStrategy(
                                HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE
                        )
                )
        );
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        TimeUnit.MILLISECONDS.sleep(100);
        return "Hello " + name + ",current thread:" + Thread.currentThread().getName();
    }

    @Override
    protected String getFallback() {
        return "【调用阻塞】：" + Thread.currentThread().getName();
    }

    public static void main(String[] args) {
        SemaphoreCommand semaphoreCommand = new SemaphoreCommand("semaphore-command");
        semaphoreCommand.testExecute();
    }

    public void testExecute() {
        for (int i = 0; i < 20; i++) {
            new Thread(new Task(countDownLatch)).start();
        }
    }

    public class Task implements Runnable {

        private CountDownLatch countDownLatch;

        public Task(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            countDownLatch.countDown();
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SemaphoreCommand semaphoreCommand = new SemaphoreCommand("semaphore-command");
            System.out.println(semaphoreCommand.execute());
        }
    }

}
```

# 四、熔断降级
1. 模拟10s内发送20个失败的请求，触发熔断器开关打开
2. 等待熔断器半打开，执行一个请求
```
public class RequestErrorCommand extends HystrixCommand<String> {

    private String name;

    private Integer count;

    public RequestErrorCommand(String name, Integer count) {
        super(HystrixCommandGroupKey.Factory.asKey("RequestErrorCommand"));
        this.name = name;
        this.count = count;
    }

    @Override
    protected String run() throws Exception {
        if (count < 20) {
            throw new RuntimeException("【模拟调用失败】");
        }
        return "Hello " + name + ",current thread:" + Thread.currentThread().getName();
    }

    @Override
    protected String getFallback() {
        return "【调用服务阻塞】";
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 25; i++) {
            Thread.sleep(500);
            RequestErrorCommand requestErrorCommand = new RequestErrorCommand("error", i);
            System.out.println(requestErrorCommand.execute() + "======>" + i + "===>【熔断器是否打开】：" + requestErrorCommand.isCircuitBreakerOpen());
        }

        Thread.sleep(3000);
        RequestErrorCommand requestErrorCommand = new RequestErrorCommand("error", 1000);
        System.out.println(requestErrorCommand.execute());
    }
}
```

# 五、请求缓存
==注意==：请求缓存只针对同一个context下才有效
```
public class RequestCacheCommand extends HystrixCommand<String> {

    private Integer id;

    public RequestCacheCommand(Integer id) {
        super(HystrixCommandGroupKey.Factory.asKey("request-cache"));
        this.id = id;
    }

    @Override
    protected String run() throws Exception {
        return "current thread " + Thread.currentThread().getName() + " execute " + id;
    }

    @Override
    protected String getCacheKey() {
        return String.valueOf(id);
    }

    public static void main(String[] args){
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            RequestCacheCommand command2a = new RequestCacheCommand(2);
            RequestCacheCommand command2b = new RequestCacheCommand(2);
            command2a.execute();
            command2b.execute();
            // isResponseFromCache判定是否是在缓存中获取结果
            System.out.println("是否从缓存中读取结果：" + command2a.isResponseFromCache());
            System.out.println("是否从缓存中读取结果：" + command2b.isResponseFromCache());
        } finally {
            context.shutdown();
        }

        context = HystrixRequestContext.initializeContext();
        try {
            RequestCacheCommand command3b = new RequestCacheCommand(2);
            System.out.println(command3b.isResponseFromCache());
        } finally {
            context.shutdown();
        }
    }
}
```

# 六、请求合并
1. 定义实体
```
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
2. 定义业务方法
```
public class UserService {

    public List<User> listUser(List<Integer> userIdList) {
        System.out.println("【请求参数】：" + userIdList);
        List<User> userList = new ArrayList<>();
        for (Integer userId : userIdList) {
            User user = User.builder()
                    .id(userId)
                    .name("测试" + userId)
                    .build();
            userList.add(user);
        }

        return userList;
    }
}

```
3. 定义comand
```
public class UserCommand extends HystrixCommand<List<User>> {

    private List<Integer> userIdList;

    private UserService userService;

    public UserCommand(List<Integer> userIdList, UserService userService) {
        super(HystrixCommandGroupKey.Factory.asKey("UserCommand"));
        this.userIdList = userIdList;
        this.userService = userService;
    }

    @Override
    protected List<User> run() throws Exception {
        List<User> userList = userService.listUser(userIdList);
        System.out.println(userList);
        return userList;
    }
}
```
4. 定义基于请求范围内的请求合并
```
public class UserCommandCollapser extends HystrixCollapser<List<User>, User, Integer> {

    private Integer userId;

    private UserService userService;

    public UserCommandCollapser(Integer userId, UserService userService) {
        super(Setter
                .withCollapserKey(HystrixCollapserKey.Factory.asKey("UserCommandCollapser"))
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(100)));
        this.userId = userId;
        this.userService = userService;
    }

    @Override
    public Integer getRequestArgument() {
        return userId;
    }

    @Override
    protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, Integer>> collapsedRequests) {
        List<Integer> userIdList = new ArrayList<>(collapsedRequests.size());
        userIdList.addAll(collapsedRequests.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList()));
        return new UserCommand(userIdList, userService);
    }

    @Override
    protected void mapResponseToRequests(List<User> batchResponse, Collection<CollapsedRequest<User, Integer>> collapsedRequests) {
        int count = 0;

        for (CollapsedRequest<User, Integer> collapsedRequest : collapsedRequests) {
            User user = batchResponse.get(count++);
            collapsedRequest.setResponse(user);
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        UserService userService = new UserService();

        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            Future<User> f1 = new UserCommandCollapser(10001, userService).queue();
            Future<User> f2 = new UserCommandCollapser(10002, userService).queue();
            Future<User> f3 = new UserCommandCollapser(10003, userService).queue();
            Future<User> f4 = new UserCommandCollapser(10004, userService).queue();
            Future<User> f5 = new UserCommandCollapser(10005, userService).queue();

            f1.get();
            f2.get();
            f3.get();
            f4.get();
            f5.get();

            System.out.println("execute command size：" + HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().size());
        } finally {
            context.shutdown();
        }
    }
}
```
5. 定义基于全局范围内的请求合并
```
public class UserCommandCollapser1 extends HystrixCollapser<List<User>, User, Integer> {

    private Integer userId;

    private UserService userService;

    public UserCommandCollapser1(Integer userId, UserService userService) {
        super(Setter
                .withCollapserKey(HystrixCollapserKey.Factory.asKey("UserCommandCollapser"))
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(100))
                .andScope(Scope.GLOBAL));
        this.userId = userId;
        this.userService = userService;
    }

    @Override
    public Integer getRequestArgument() {
        return userId;
    }

    @Override
    protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, Integer>> collapsedRequests) {
        List<Integer> userIdList = new ArrayList<>(collapsedRequests.size());
        userIdList.addAll(collapsedRequests.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList()));
        return new UserCommand(userIdList, userService);
    }

    @Override
    protected void mapResponseToRequests(List<User> batchResponse, Collection<CollapsedRequest<User, Integer>> collapsedRequests) {
        int count = 0;

        for (CollapsedRequest<User, Integer> collapsedRequest : collapsedRequests) {
            User user = batchResponse.get(count++);
            collapsedRequest.setResponse(user);
        }
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        UserService userService = new UserService();
        Thread thread1 = new Thread(() -> {
            try {
                test1(userService);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                test2(userService);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread3 = new Thread(() -> {
            try {
                test3(userService);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread4 = new Thread(() -> {
            try {
                test4(userService);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread5 = new Thread(() -> {
            try {
                test5(userService);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
    }

    public static void test1(UserService userService) throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            Future<User> f1 = new UserCommandCollapser1(10001, userService).queue();

            System.out.println("【请求1】：" + f1.get());

            System.out.println("execute command size：" + HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().size());
        } finally {
            context.shutdown();
        }
    }

    public static void test2(UserService userService) throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            Future<User> f2 = new UserCommandCollapser1(10002, userService).queue();


            System.out.println("【请求2】：" + f2.get());

            System.out.println("execute command size：" + HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().size());
        } finally {
            context.shutdown();
        }
    }

    public static void test3(UserService userService) throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            Future<User> f3 = new UserCommandCollapser1(10003, userService).queue();


            System.out.println("【请求3】：" + f3.get());

            System.out.println("execute command size：" + HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().size());
        } finally {
            context.shutdown();
        }
    }

    public static void test4(UserService userService) throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            Future<User> f4 = new UserCommandCollapser1(10004, userService).queue();

            System.out.println("【请求4】：" + f4.get());

            System.out.println("execute command size：" + HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().size());
        } finally {
            context.shutdown();
        }
    }

    public static void test5(UserService userService) throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            Future<User> f5 = new UserCommandCollapser1(10005, userService).queue();


            System.out.println("【请求5】：" + f5.get());

            System.out.println("execute command size：" + HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().size());
        } finally {
            context.shutdown();
        }
    }
}
```