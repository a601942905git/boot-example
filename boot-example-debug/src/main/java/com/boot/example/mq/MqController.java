package com.boot.example.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.example.mq.MqController
 *
 * 手动开启、停止RabbitMq消费者
 *
 * @author lipeng
 * @date 2020/11/24 5:47 PM
 */
@RestController
@Slf4j
public class MqController {

    @Autowired
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;

    /**
     * 开始监听容器
     *
     * @param containerId 容器id
     * @return 操作结果
     */
    @GetMapping("/start/{containerId}")
    public String startRabbitMessageListenerContainer(@PathVariable(value = "containerId") String containerId) {
        rabbitListenerEndpointRegistry.getListenerContainer(containerId).start();
        return "container id：" + containerId + " start";
    }

    /**
     * 关闭监听容器
     *
     * @param containerId 容器id
     * @return 操作结果
     */
    @GetMapping("/stop/{containerId}")
    public String stopRabbitMessageListenerContainer(@PathVariable(value = "containerId") String containerId) {
        rabbitListenerEndpointRegistry.getListenerContainer(containerId).stop();
        return "container id：" + containerId + " stop";
    }
}
