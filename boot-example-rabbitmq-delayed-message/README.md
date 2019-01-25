# 消息延迟使用案例
比如用户成功下单，却未支付，要求对30分钟未支付的订单进行取消操作

==解决方案：== 
1. 我们可以通过定时任务来扫描数据库中的数据，但是由于数据量比较大，我们不可能一次把所有的数据都查询出来，并且数据查询出来之后还需要进行业务处理，会导致某些订单可能到31、32、33、34、35或者更长时间才被取消，而且会影响系统性能
2. 通过MQ的延迟消息来实现，我们把消息发送到MQ服务器，设置时间为我们的订单取消时间限制，比如30分钟，那么30分钟之后MQ服务才会把消息推送给消费者进行取消订单操作，这样可以做到实时取消订单

# 安装延迟消息插件
切换到RabbitMQ安装目录的plugins目录
```
wget https://dl.bintray.com/rabbitmq/community-plugins/3.7.x/rabbitmq_delayed_message_exchange/rabbitmq_delayed_message_exchange-20171201-3.7.x.zip
```
解压压缩包
```
tar -zxvf rabbitmq_delayed_message_exchange-20171201-3.7.x.zip
```
切换到sbin目录下面,启用该插件
```
./rabbitmq-plugins enable rabbitmq_delayed_message_exchange
```
重启RabbitMQ服务
```
./rabbitmqctl stop
./rabbitmq-server -detached
```

# 代码示例
配置类
```
@Configuration
public class RabbitmqConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback{

    public static final String EXCHANGE   = "spring-boot-delayed-exchange";
    public static final String ROUTING_KEY = "spring-boot-delayed-routingKey";
    public static final String QUEUE_NAME = "boot-example-delayed-queue";

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
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>(16);
        args.put("x-delayed-type", "direct");
        return new CustomExchange(EXCHANGE, "x-delayed-message",
                true, false, args);
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
        return BindingBuilder
                .bind(queue())
                .to((delayedExchange()))
                .with(RabbitmqConfig.ROUTING_KEY)
                .noargs();
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

生产者
```
@Component
public class HelloProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg() {
        LocalDateTime localDateTime = LocalDateTime.now();
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(
                RabbitmqConfig.EXCHANGE,
                RabbitmqConfig.ROUTING_KEY, "hello rabbitMQ，发送时间：" + localDateTime,
                message -> {
                    message.getMessageProperties().setHeader("x-delay", 3000);
                    return message;
                },
                correlationData);
    }
}
```

消费者
```
@Component
@RabbitListener(queues = RabbitmqConfig.QUEUE_NAME)
public class HelloConsumer {

    @RabbitHandler
    public void helloConsumer(String content, Channel channel, Message message) throws IOException {
        LocalDateTime localDateTime = LocalDateTime.now();
        Long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.println("【receive message】：" + content + "，消费时间：" + localDateTime);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            // 当出现异常的时候，拒绝消息，并将消息放入队列中，这样可以进行再次消费
            channel.basicNack(deliveryTag, false, true);
        }
    }
}
```

启动类
```
@SpringBootApplication
public class RabbitmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqApplication.class, args);
    }
}
```

测试类
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

打印结果
```
【message】：(Body:'hello rabbitMQ，发送时间：2019-01-25T15:20:11.043' MessageProperties [headers={spring_returned_message_correlation=f1af3d3d-9005-4748-8e00-91c47be293ab}, contentType=text/plain, contentEncoding=UTF-8, contentLength=0, receivedDeliveryMode=PERSISTENT, priority=0, receivedDelay=3000, deliveryTag=0])
【replyCode】：312
【replyText】：NO_ROUTE
【exchange】：spring-boot-delayed-exchange
【routingKey】：spring-boot-delayed-routingKey
【消息】：f1af3d3d-9005-4748-8e00-91c47be293ab，发送成功
【message】：(Body:'hello rabbitMQ，发送时间：2019-01-25T15:20:12.062' MessageProperties [headers={spring_returned_message_correlation=94f88a75-97e2-46ba-a771-de15e4102610}, contentType=text/plain, contentEncoding=UTF-8, contentLength=0, receivedDeliveryMode=PERSISTENT, priority=0, receivedDelay=3000, deliveryTag=0])
【replyCode】：312
【replyText】：NO_ROUTE
【exchange】：spring-boot-delayed-exchange
【routingKey】：spring-boot-delayed-routingKey
【消息】：94f88a75-97e2-46ba-a771-de15e4102610，发送成功
【message】：(Body:'hello rabbitMQ，发送时间：2019-01-25T15:20:13.063' MessageProperties [headers={spring_returned_message_correlation=a2848e8e-26bd-44ef-93bb-b042866bd981}, contentType=text/plain, contentEncoding=UTF-8, contentLength=0, receivedDeliveryMode=PERSISTENT, priority=0, receivedDelay=3000, deliveryTag=0])
【replyCode】：312
【replyText】：NO_ROUTE
【exchange】：spring-boot-delayed-exchange
【routingKey】：spring-boot-delayed-routingKey
【消息】：a2848e8e-26bd-44ef-93bb-b042866bd981，发送成功
【receive message】：hello rabbitMQ，发送时间：2019-01-25T15:20:11.043，消费时间：2019-01-25T15:20:14.060
```
从控制台可以看出来，每发送3条消息，会出现一条消息被消费，由于生产者控制每1s发送一条消息，所有恰好过3s，Broker会将消息推送给消费者

==注意：== 但是我们从打印结果中看到调用了ReturnCallback回调，该回调执行说明Exchange没有找到对应的队列，但是最终消费者还是成功消费了消息。

==大胆设想就是延迟消息原理如下：==

就是我们发送延迟消息给Broker，此时消息停留在Exchange，并且无法得知此消息的routingKey，所以会一直回调ReturnCallback函数，3s之后就可以得知该消息的routingKey，消息就可以发送到队列，然后推送给消费者。
