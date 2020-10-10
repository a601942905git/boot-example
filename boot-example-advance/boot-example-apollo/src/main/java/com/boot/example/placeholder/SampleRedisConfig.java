package com.boot.example.placeholder;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * com.boot.example.placeholder.SampleRedisConfig
 *
 * @author lipeng
 * @date 2020/2/25 下午4:32
 */
@ConfigurationProperties(prefix = "redis.cache")
@Component
@RefreshScope
@Data
@ToString
public class SampleRedisConfig {
    private int expireSeconds;
    private int commandTimeout;
}
