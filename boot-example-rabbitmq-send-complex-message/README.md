# 消息传送
只要涉及到消息的网络传输，肯定就会涉及到序列化、反序列化，显然我们需要定义发送序列化方式和接受反序列化方式

# 配置
```
@Configuration
public class RabbitmqConfig {

    public static final String EXCHANGE   = "spring-boot-exchange";
    public static final String ROUTING_KEY = "spring-boot-routingKey";
    public static final String QUEUE_NAME = "boot-example-queue";

    public static final String ROUTING_OBJECT_KEY = "spring-boot-routingKey-object";
    public static final String QUEUE_OBJECT_NAME = "boot-example-queue-object";

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
        connectionFactory.setPublisherConfirms(true);
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
        // 指定序列化方式为jackson
        template.setMessageConverter(new Jackson2JsonMessageConverter());
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
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public Queue queue1() {
        // 队列持久
        return new Queue(QUEUE_OBJECT_NAME);
    }

    /**
     * 将队列绑定到exchange，并表明路由的key
     * @return
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(defaultExchange()).with(RabbitmqConfig.ROUTING_KEY);
    }

    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(queue1()).to(defaultExchange()).with(RabbitmqConfig.ROUTING_OBJECT_KEY);
    }

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // 指定反序列化方式为jackson
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }
}
```

# 发送者
```
@Component
public class HelloProducer implements RabbitTemplate.ConfirmCallback{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg() {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE,
                RabbitmqConfig.ROUTING_KEY, "hello rabbitMQ", correlationData);
    }

    public void sendObjectMsg() {
        User user = User.builder().id(10001).name("测试发送消息对象").build();
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE,
                RabbitmqConfig.ROUTING_OBJECT_KEY, user, correlationData);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            System.out.println("消息：" + correlationData.getId() + "，发送成功");
        } else {
            System.out.println("消息：" + correlationData.getId() + "，发送失败，失败原因：" + cause);
        }
    }
}
```

# 消费者
```
@Component
@RabbitListener(queues = RabbitmqConfig.QUEUE_OBJECT_NAME)
public class HelloConsumer1 {

    @RabbitHandler
    public void helloConsumer(User user) {
        System.out.println("【receive message】：" + user);
    }
}
```

# 测试
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
            // helloProducer.sendMsg();
            helloProducer.sendObjectMsg();
        }
    }
}
```