# RabbitMQ重要术语
1. Broker：消息队列服务实体
2. Exchange：交换机，通过特定的方法路由到指定的队列
3. Queue：消息载体，每个消息都会被路由到一个或多个队列
4. Binding：绑定，把Exchange和Queue按照路由规则绑定起来
5. RoutingKey：路由的key，Exchange根据该值进行消息投递
6. Vhost：一个Broker可以创建多个Vhost，用作不同用户的权限分离
7. Producer：消息生产者
8. Consumer：消息消费者
9. Channel：消息通道，一个连接可以创建多个Channel，一个Channel代表一个会话任务

# RabbitMQ配置
```java
@Configuration
public class RabbitmqConfig {

    public static final String EXCHANGE   = "spring-boot-exchange";
    public static final String ROUTING_KEY = "spring-boot-routingKey";
    public static final String QUEUE_NAME = "boot-example-queue";

    /**
     * 创建连接工厂
     * @return
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("127.0.0.1:5672");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("boot-example");
        return connectionFactory;
    }

    /**
     * 创建RabbitTemplate
     * @return
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }

    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     *
     * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     * HeadersExchange ：通过添加属性key-value匹配
     * DirectExchange:按照routingkey分发到指定队列
     * TopicExchange:多关键字匹配
     */
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(EXCHANGE);
    }

    /**
     * 创建队列
     * @return
     */
    @Bean
    public Queue queue() {
        // 队列持久
        return new Queue(QUEUE_NAME, true);

    }

    /**
     * 将队列绑定到exchange，并表明路由的key
     * @return
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(defaultExchange()).with(RabbitmqConfig.ROUTING_KEY);
    }
}
```

# 生产者
```java
@Component
public class HelloProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg() {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertSendAndReceive(RabbitmqConfig.EXCHANGE,
                RabbitmqConfig.ROUTING_KEY, "hello rabbitMQ", correlationData);
    }
}
```

# 消费者
```java
@Component
@RabbitListener(queues = RabbitmqConfig.QUEUE_NAME)
public class HelloConsumer {

    @RabbitHandler
    public void helloConsumer(String message) {
        System.out.println("【receive message】：" + message);
    }
}
```

# 启动类
```java
@SpringBootApplication
public class RabbitmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqApplication.class, args);
    }
}
```

# 测试类
```java
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RabbitmqApplication.class)
public class RabbitmqTest {

    @Autowired
    private HelloProducer helloProducer;

    @Test
    public void sendMessage() throws InterruptedException {
        while (true) {
            TimeUnit.SECONDS.sleep(1);
            helloProducer.sendMsg();
        }
    }
}
```