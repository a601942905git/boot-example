# 消息发送回调
回调指的是：生产者发送消息到MQ服务，MQ服务会回调我们指定的回调函数，这样我们可以在回调中得知消息是否投递成功，如果投递失败我们需要如何补偿。


# 配置
```
@Bean
public ConnectionFactory connectionFactory() {
    CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
    connectionFactory.setAddresses("127.0.0.1:5672");
    connectionFactory.setUsername("guest");
    connectionFactory.setPassword("guest");
    connectionFactory.setVirtualHost("boot-example");
    // 开启Confirm
    connectionFactory.setPublisherConfirms(true);
    return connectionFactory;
}
```

# 生产者
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
此处注意一定要设置：==connectionFactory.setPublisherConfirms(true);==

# Broker回调ack为false的情况
当生产者与Broker之间的连接断开或者发送网络抖动，我们可以在程序运行的时候，在RabbitMQ控制台强制断开connection来模拟ack为false的情况。