# 主题
RabbitMQ Exchange类型为Topic，原理类似Direct，都需要BindingKey和RoutingKey，不过Topic是模糊匹配，使用“*”代表任意一个字符，“#”代表任意一个或者多个字符

# 示例
1. 配置
```
@Configuration
public class RabbitmqConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback{

    public static final String EXCHANGE   = "spring-boot-topic-exchange";
    public static final String QUEUE_NAME = "boot-example-topic-queue";
    public static final String BINDING_KEY = "com.#";

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
        connectionFactory.setPublisherReturns(true);
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
        template.setMandatory(true);
        template.setConfirmCallback(this);
        template.setReturnCallback(this);
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
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE);
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

    /**
     * 将队列绑定到exchange，并表明路由的key
     * @return
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with(BINDING_KEY);
    }


    /**
     * 当消息发送到Broker的Exchange中，该方法被调用
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            System.out.println("【消息】：" + correlationData.getId() + "，发送成功");
        } else {
            System.out.println("【消息】：" + correlationData.getId() + "，发送失败");
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("【message】：" + message);
        System.out.println("【replyCode】：" + replyCode);
        System.out.println("【replyText】：" + replyText);
        System.out.println("【exchange】：" + exchange);
        System.out.println("【routingKey】：" + routingKey);
    }
}
```
2. 生产者
```
@Component
public class HelloProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg() {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE,
                "com.boot.example", "hello rabbitMQ", correlationData);

        correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE,
                "com.test", "hello rabbitMQ", correlationData);
    }
}
```
3. 消费者
```
@Component
@RabbitListener(queues = RabbitmqConfig.QUEUE_NAME)
public class HelloConsumer {

    @RabbitHandler
    public void helloConsumer(String content, Message message) {
        Object corRelationId = message.getMessageProperties().getHeaders().get(PublisherCallbackChannelImpl.RETURNED_MESSAGE_CORRELATION_KEY);
        System.out.println("【consumer1 boot-example-fanout-queue receive message】：" + content + "，【corRelationId】：" + corRelationId);
    }
}
```
4. 测试
```
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RabbitmqApplication.class)
public class RabbitmqTest {

    @Autowired
    private HelloProducer helloProducer;

    @Test
    public void sendMessage() throws InterruptedException, IOException {
        helloProducer.sendMsg();
    }
}
```
5. 测试结果
```
【消息】：d102505e-7ccf-4079-b972-45835ea90ba5，发送成功
【consumer1 boot-example-fanout-queue receive message】：hello rabbitMQ，【corRelationId】：d102505e-7ccf-4079-b972-45835ea90ba5
【消息】：9dc9ea81-83d4-41a1-b225-3b0c3000229d，发送成功
【consumer1 boot-example-fanout-queue receive message】：hello rabbitMQ，【corRelationId】：9dc9ea81-83d4-41a1-b225-3b0c3000229d
```

==总结：== 从测试结果可以看出来，我们设置的RoutingKey为"com.boot.example"和"com.test"都发送到了队列中，并且成功被消费者消费，这就是Topic。