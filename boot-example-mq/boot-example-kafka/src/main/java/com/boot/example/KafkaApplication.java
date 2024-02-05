package com.boot.example;

import org.apache.kafka.clients.ClientResponse;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.internals.AbstractCoordinator;
import org.apache.kafka.clients.consumer.internals.ConsumerCoordinator;
import org.apache.kafka.clients.producer.internals.Sender;
import org.apache.kafka.common.utils.Timer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;

/**
 * com.boot.example.KafkaApplication
 * 加入消费者组：
 * @see KafkaConsumer#poll(Timer, boolean)
 * @see ConsumerCoordinator#poll(Timer, boolean)
 * @see AbstractCoordinator#ensureActiveGroup(Timer)
 *
 * 修改偏移量：
 * @see KafkaConsumer#updateFetchPositions(Timer)
 *
 * 重平衡：
 * @see AbstractCoordinator.HeartbeatResponseHandler 错误类型为：Errors.REBALANCE_IN_PROGRESS
 *
 * 回调：
 * @see Sender#run()
 * @see Sender#sendProduceRequest(long, int, short, int, List)
 * @see Sender#handleProduceResponse(ClientResponse, Map, long)
 *
 * @author lipeng
 * @date 2020/2/20 下午5:35
 */
@SpringBootApplication
public class KafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }
}
