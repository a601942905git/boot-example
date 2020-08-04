# 死信队列
死信队列就是先将设置了过期时间的消息发送至Exchange，然后路由到绑定的队列中，该队列没有消费者，所以消息一直存放在队列中，由于设置了过期时间，等消息过期之后，将消息重新路由到一个新的队列中，然后进行消费，从而达到和延迟队列一样的效果

# 死信消息场景
1. 设置消息过期时间
2. 设置消息队列过期时间
3. 消息被消费者拒绝，并且未重新放入队列中

# 原理图
![](https://wolf-heart.oss-cn-beijing.aliyuncs.com/20190216/%E6%9C%AA%E5%91%BD%E5%90%8D%E6%96%87%E4%BB%B6.png)

# 示例代码
1. 配置类
```
@Configuration
public class RabbitmqConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback{

    public static final String EXCHANGE = "dead-letter-exchange";
    public static final String REDIRECT_ROUTING_KEY = "redirect-routingKey";
    public static final String REDIRECT_QUEUE_NAME = "redirect-queue";
    public static final String DLX_QUEUE_NAME = "dlx-queue";
    public static final String DLX_ROUTING_KEY = "dlx-routing-key";

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

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    /**
     * 创建队列
     * @return
     */
    @Bean
    public Queue directQueue() {
        // 队列持久
        return new Queue(REDIRECT_QUEUE_NAME);
    }

    @Bean
    public Queue dlxQueue() {
        Map<String, Object> params = new HashMap<>();
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        params.put("x-dead-letter-exchange", EXCHANGE);
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
        params.put("x-dead-letter-routing-key", REDIRECT_ROUTING_KEY);
        return new Queue(DLX_QUEUE_NAME, true, false, false, params);
    }

    /**
     * 将队列绑定到exchange，并表明路由的key
     * @return
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(directQueue()).to(exchange()).with(RabbitmqConfig.REDIRECT_ROUTING_KEY);
    }

    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(dlxQueue()).to(exchange()).with(RabbitmqConfig.DLX_ROUTING_KEY);
    }

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        /**
         * 默认为自动确认模式，此处改成手动，这样我们可以在逻辑代码执行完成的情况下进行手动确认
         * 保证消息一定被消费掉
         */
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
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
        System.out.println("【send message time】：" + LocalDateTime.now());
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE,
                RabbitmqConfig.DLX_ROUTING_KEY, "hello rabbitMQ",
                message -> {
                    message.getMessageProperties().setExpiration(String.valueOf(3000));
                    return message;
                },
                correlationData);
    }
}
```
3. 消费者
```
@Component
@RabbitListener(queues = RabbitmqConfig.REDIRECT_QUEUE_NAME)
public class HelloConsumer {

    @RabbitHandler
    public void helloConsumer(String content, Channel channel, Message message) throws IOException {
        LocalDateTime localDateTime = LocalDateTime.now();
        Long deliveryTag = message.getMessageProperties().getDeliveryTag();
        Object correlationId = message.getMessageProperties()
                .getHeaders()
                .get(PublisherCallbackChannelImpl.RETURNED_MESSAGE_CORRELATION_KEY);
        try {
            System.out.println("【receive message】：" + content + "，correlationId：" + correlationId + "，消费时间：" + localDateTime);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            // 当出现异常的时候，拒绝消息，并将消息放入队列中，这样可以进行再次消费
            channel.basicNack(deliveryTag, false, true);
        }
    }
}
```
4. 启动类
```
@SpringBootApplication
public class RabbitmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqApplication.class, args);
    }
}
```
5. 测试类
```
@SpringBootApplication
public class RabbitmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqApplication.class, args);
    }
}
```
6. 测试结果
```
【send message time】：2019-02-16T15:25:09.491
【消息】：35246a58-5425-4c48-a987-b0c5b3a7bbd7，发送成功
【receive message】：hello rabbitMQ，correlationId：35246a58-5425-4c48-a987-b0c5b3a7bbd7，消费时间：2019-02-16T15:25:12.509
```
从测试结果可以看出来，该消息过了3秒钟才进行消费，正如我们之前理论知识描述的一直。

# 问题
在进行死信队列集成的时候，出现了以下问题
```
Channel shutdown: channel error; protocol method: #method<channel.close>(reply-code=406, reply-text=PRECONDITION_FAILED - unknown delivery tag 1, class-id=60, method-id=80)
```
从描述我们可以看到，找不到delivery tag 1的消息，说明该消息已经被确认过了，原因如下，配置文件中没有消费者配置，默认情况下确定消息为自动确认，而我又使用了channel.basicAck(deliveryTag, false);进行手动确认，所以就出现重复确认的问题。

解决方法：

进行消费者配置
```
@Bean
public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory() {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory());
    /**
     * 默认为自动确认模式，此处改成手动，这样我们可以在逻辑代码执行完成的情况下进行手动确认
     * 保证消息一定被消费掉
     */
    factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
    return factory;
}
```
设定消费者确认模式为手动模式，这样就不会存在两次确认的情况