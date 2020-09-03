package com.boot.example.placeholder;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.placeholder.MqConfig
 *
 * @author lipeng
 * @date 2020/8/28 1:56 PM
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.rabbitmq")
@RefreshScope
public class RabbitmqConfig {

    private String address;

    private String port;
}
