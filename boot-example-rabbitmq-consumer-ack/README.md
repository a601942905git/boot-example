# 消费者Ack
Ack的作用：当Broker将消息推送给消费者，消费者在消费完消息后需要向Broker发送一个应答，告诉Broker已经将消息处理完毕，可以从队列中删除此条消息。

# 如果消费者不进行Ack？
如果消费者在消费完消息之后不进行Ack的话，我们在控制台Queue选项中可以看到有很多unack的消息，当停掉我们的应用也就是断开和Broker之间的连接，这些unack的消息就会变成等待消费的消息，当我们重启应用的时候，Broker会将这些消息重新发送给我们的消费者，就会导致消费者重复消费消息。

# Nack的作用
可以用来拒绝一个或多个消息，第三个参数requeue如果为true，那么会将该条消息重新放入队列[官网解释](https://www.rabbitmq.com/nack.html)，官网的解释就是会将此消息放入该消息之前在队列中的位置，如果不是，就将该消息放入靠近队列头部的位置。

# 不Ack和Nack的区别
看到了Nack的作用，拒绝消息，如果requeue为true，则将消息重新放入队列，如果为false，那么则将该消息丢弃掉。那么这和不进行Ack有什么区别，之前我们了解到，不进行Ack，Broker就不会将该消息删除，消费者重启的时候，会重新发送该消息。

区别：
如果不进行Ack，只有一个消费者的时候，如果想消费该消息，那么只有重启消费者应用；如果有多个消费者，当前消费者需要与Broker断开连接，这样Broker才会将消费发送给其它消费者

调用Nack并且requeue设置为true，会将消息重新放入队列，只有一个消费者的时候，会将消息重新发送给该消费者；如果有多个消费者，那么重新放入队列中的消息将会轮询消费者，当轮询到的消费者也调用Nack并且requeue设置为true，那么消息会被放入队列，轮询给下一个消费者。


# 消费者代码
```
@Component
@RabbitListener(queues = RabbitmqConfig.QUEUE_OBJECT_NAME)
public class HelloConsumer1 {

    @RabbitHandler
    public void helloConsumer(User user, Channel channel, Message message) throws IOException {
        Object corRelationId = message.getMessageProperties().getHeaders().get(PublisherCallbackChannelImpl.RETURNED_MESSAGE_CORRELATION_KEY);
        try {
            System.out.println("【consumer1 receive message】：" + corRelationId + "，content" + user);
            throw new NullPointerException();
            // channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}
```
我们可以对catch中的代码进行注释、进行不注释分别查看效果，就可以加深对Nack的作用。