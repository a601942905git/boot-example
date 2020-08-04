# 发布订阅
发布订阅使用场景有很多，比如用户注册成功之后，需要发送短信、赠送积分等操作。

# RabbitMQ发布订阅实现
1. 使用FanoutExchange
```
@Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE);
    }
```
2. 将队列和Exchange进行绑定
```
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
    return new Queue(QUEUE_NAME1);
}

/**
 * 将队列绑定到exchange，并表明路由的key
 * @return
 */
@Bean
public Binding binding() {
    return BindingBuilder.bind(queue()).to(fanoutExchange());
}

/**
 * 将队列绑定到exchange，并表明路由的key
 * @return
 */
@Bean
public Binding binding1() {
    return BindingBuilder.bind(queue1()).to(fanoutExchange());
}
```
3. 生产者发送消息不需要指定RoutingKey
```
@Component
public class HelloProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg() {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE,
                "", "hello rabbitMQ", correlationData);
    }
}
```
4. 消费者1
```
@Component
@RabbitListener(queues = RabbitmqConfig.QUEUE_NAME)
public class HelloConsumer {

    @RabbitHandler
    public void helloConsumer(String message) {
        System.out.println("【consumer1 boot-example-fanout-queue receive message】：" + message);
    }
}
```
5. 消费者2
```
@Component
@RabbitListener(queues = RabbitmqConfig.QUEUE_NAME1)
public class HelloConsumer1 {

    @RabbitHandler
    public void helloConsumer(String message) {
        System.out.println("【consumer2 receive message】：" + message);
    }
}
```
6. 测试类
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
可以看到每次发送消息，都会被消费两次，达到了发布订阅的目的

如果我们在添加一个消费者，和消费者1监听同样的队列，会出现什么现象呢？
7. 消费者3
```
@Component
@RabbitListener(queues = RabbitmqConfig.QUEUE_NAME)
public class HelloConsumer2 {

    @RabbitHandler
    public void helloConsumer(String message) {
        System.out.println("【consumer3 boot-example-fanout-queue receive message】：" + message);
    }
}
```
8. 执行测试类，查看控制台
```
【consumer2 boot-example-fanout-queue1 receive message】：hello rabbitMQ，【corRelationId】：d1d25050-c3cf-4ed1-a48c-5919d6ea9471
【consumer1 boot-example-fanout-queue receive message】：hello rabbitMQ，【corRelationId】：d1d25050-c3cf-4ed1-a48c-5919d6ea9471
【消息】：d1d25050-c3cf-4ed1-a48c-5919d6ea9471，发送成功
【consumer2 boot-example-fanout-queue1 receive message】：hello rabbitMQ，【corRelationId】：332cce21-60c3-4dd2-bd60-6b60fa8edafe
【consumer3 boot-example-fanout-queue1 receive message】：hello rabbitMQ，【corRelationId】：332cce21-60c3-4dd2-bd60-6b60fa8edafe
【消息】：332cce21-60c3-4dd2-bd60-6b60fa8edafe，发送成功
【consumer1 boot-example-fanout-queue receive message】：hello rabbitMQ，【corRelationId】：49b4e9e0-6b5f-4c68-bbd3-aa9150fcc6ce
【consumer2 boot-example-fanout-queue1 receive message】：hello rabbitMQ，【corRelationId】：49b4e9e0-6b5f-4c68-bbd3-aa9150fcc6ce
【消息】：49b4e9e0-6b5f-4c68-bbd3-aa9150fcc6ce，发送成功
【consumer2 boot-example-fanout-queue1 receive message】：hello rabbitMQ，【corRelationId】：a8e73380-8c4d-40e9-905a-e2b5979fe91d
【consumer3 boot-example-fanout-queue1 receive message】：hello rabbitMQ，【corRelationId】：a8e73380-8c4d-40e9-905a-e2b5979fe91d
【消息】：a8e73380-8c4d-40e9-905a-e2b5979fe91d，发送成功
```

==总结：== 从以上打印内容我们可以看出来，生产者发送消息给Exchange，Exchange将消息发送给所有的Queue，Queue再将消息发送给消费者，但是队列1有两个消费者，所以队列1会将消息以==轮询== 的方式发送给消费者