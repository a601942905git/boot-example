package com.boot.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * com.boot.example.HelloConsumer
 *
 * @author lipeng
 * @date 2019-01-13 15:24
 */
@Component
public class HelloConsumer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        new Thread(() -> {
            while (true) {
                rabbitTemplate.execute(new ChannelCallbackImpl(RabbitmqConfig.QUEUE_NAME));
            }
        }).start();
    }

    public class ChannelCallbackImpl implements ChannelCallback<String> {
        private String queueName;

        public ChannelCallbackImpl(String queueName) {
            this.queueName = queueName;
        }

        @Override
        public String doInRabbit(Channel channel) throws Exception {
            GetResponse response = channel.basicGet(this.queueName, false);
            if (null == response) {
                return null;
            }
            String content = new String(response.getBody(), Charset.defaultCharset());
            System.out.println("【consumer pull message content】：" + content);
            Long deliveryTagId = response.getEnvelope().getDeliveryTag();
            try {
                channel.basicAck(deliveryTagId, false);
            } catch (Exception e) {
                channel.basicNack(deliveryTagId, false, true);
            }
            return null;
        }
    }
}
