package com.boot.example;

import com.rabbitmq.client.AddressResolver;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Command;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.impl.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.CachingConnectionFactoryConfigurer;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;

import java.util.concurrent.ExecutorService;

/**
 * com.boot.example.RabbitmqApplication
 * 框架自动提交：
 * @see org.springframework.amqp.rabbit.listener.BlockingQueueConsumer#commitIfNecessary(boolean)
 * 配置:
 * @see CachingConnectionFactoryConfigurer#configure(CachingConnectionFactory, RabbitProperties)
 *
 * 发送消息
 * @see RabbitTemplate#convertAndSend(String, String, Object, CorrelationData)
 * 创建connection
 * @see CachingConnectionFactory#createConnection()
 * 新建connection
 * @see ConnectionFactory#newConnection(ExecutorService, AddressResolver, String)
 * 启动connection
 * @see AMQConnection#start()
 * 初始化connection
 * @see SocketFrameHandler#initialize(AMQConnection)
 * 启动主线程
 * @see AMQConnection#startMainLoop()
 * 运行主线程
 * @see AMQConnection.MainLoop#run()
 * 读取帧
 * @see AMQConnection#readFrame(Frame)
 * 处理帧
 * @see AMQChannel#handleFrame(Frame)
 * 处理已完成入站命令
 * @see AMQChannel#handleCompleteInboundCommand(AMQCommand)
 * 处理不同类型命令，比如Basic.Deliver、Basic.Return、Basic.Ack等
 * @see ChannelN#processAsync(Command)
 *
 * 创建channel
 * @see CachingConnectionFactory.ChannelCachingConnectionProxy#createChannel(boolean)
 * @see Channel#confirmSelect()
 *
 * 添加监听器(异步获取结果)
 * @see RabbitTemplate#addListener(Channel)
 * 同步获取结果
 * @see RabbitTemplate#waitForConfirms(long)
 *
 * @author lipeng
 * @date 2020/9/27 3:30 PM
 */
@SpringBootApplication
public class RabbitmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqApplication.class, args);
    }
}
