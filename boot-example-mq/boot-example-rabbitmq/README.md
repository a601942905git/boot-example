# 一、添加依赖
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

# 二、配置
```
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
```

# 三、消费者
```
@Component
@RabbitListener(queues = RabbitmqConfig.QUEUE_NAME)
public class HelloConsumer {

    @RabbitHandler
    public void helloConsumer(String message) {
        System.out.println("【receive message】：" + message);
    }
}
```

# 四、生产者
```
@Component
public class HelloProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg() {
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE,
                RabbitmqConfig.ROUTING_KEY, "hello rabbitMQ");
    }

}
```

# 五、单元测试
```
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

# 六、验证多消费者模式消息消费情况
消费者代码修改
```
@Component
@RabbitListener(queues = RabbitmqConfig.QUEUE_NAME)
public class HelloConsumer {

    @RabbitHandler
    public void helloConsumer(String message) {
        System.out.println("【consumer1 receive message】：" + message);
    }
}
```

添加一个消费者
```
@Component
@RabbitListener(queues = RabbitmqConfig.QUEUE_NAME)
public class HelloConsumer1 {

    @RabbitHandler
    public void helloConsumer(String message) {
        System.out.println("【consumer2 receive message】：" + message);
    }
}
```

生产者代码修改
```
@Component
public class HelloProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg() {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE,
                RabbitmqConfig.ROUTING_KEY, "hello rabbitMQ：" + correlationData.getId(), correlationData);
    }
}
```

测试代码修改
```
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RabbitmqApplication.class)
public class RabbitmqTest {

    @Autowired
    private HelloProducer helloProducer;

    @Test
    public void sendMessage() throws InterruptedException, IOException {
        while (true) {
            TimeUnit.SECONDS.sleep(1);
            helloProducer.sendMsg();
        }
    }
}
```

输出结果：
```
【consumer1 receive message】：hello rabbitMQ：4d40f7b1-eac5-4167-a102-9b5e04fceef4
【consumer2 receive message】：hello rabbitMQ：adfcab34-a57f-4fca-94f0-c8afc1285a1c
【consumer1 receive message】：hello rabbitMQ：8eae2e49-388e-4648-be48-e5e6e6fc916f
【consumer2 receive message】：hello rabbitMQ：6a98d482-6647-4d50-bbcb-b7659e255cf3
【consumer1 receive message】：hello rabbitMQ：fa827f68-84a0-4061-b83a-3900429e56bc
【consumer2 receive message】：hello rabbitMQ：41e0d92a-e30b-4183-a00d-09a1a81fcb97
【consumer1 receive message】：hello rabbitMQ：e1010701-53a9-4fab-941f-f24e7f6976bc
【consumer2 receive message】：hello rabbitMQ：06f92c82-6cff-4861-9845-bd57aa7b9bcc
【consumer1 receive message】：hello rabbitMQ：23f8c4d9-3e54-4d93-9989-658b70548de1
【consumer2 receive message】：hello rabbitMQ：20ea41f1-a011-41d6-85ae-2eed5633d985
【consumer1 receive message】：hello rabbitMQ：fd0b3204-41ba-48ee-b667-7053433a4afa
【consumer2 receive message】：hello rabbitMQ：3c210933-75a6-46da-b02e-6b1bb69b51cf
【consumer1 receive message】：hello rabbitMQ：a927f766-28cf-487d-9b9a-2c6273f4971e
【consumer2 receive message】：hello rabbitMQ：267e3494-d625-4fb9-bfc4-ced29aa5669e
【consumer1 receive message】：hello rabbitMQ：806c21d1-3293-4d71-acb8-2b4db09544d0
【consumer2 receive message】：hello rabbitMQ：a2d79fb5-9bca-4f55-b18c-a7dd8e0cdb1f
【consumer1 receive message】：hello rabbitMQ：a360b597-8376-4eae-a0b5-1a25bf64b690
【consumer2 receive message】：hello rabbitMQ：fefa2918-bf69-4f67-9607-2fdc10d72009
【consumer1 receive message】：hello rabbitMQ：aa02be5d-7a20-47cf-8509-cc98da2a6cb5
【consumer2 receive message】：hello rabbitMQ：187cb021-20da-4aa4-94a2-f6a2079bfc5f
```
==总结：== 从输出结果可以看出，如果存在多个消费者，那么Broker会以轮询的方式将消息发送给消费者