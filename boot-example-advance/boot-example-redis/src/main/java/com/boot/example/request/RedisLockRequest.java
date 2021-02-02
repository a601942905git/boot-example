package com.boot.example.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.RedisLockRequest
 *
 * @author lipeng
 * @date 2021/2/2 10:53 AM
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RedisLockRequest {

    private String key;

    private Long expireTime;

    private TimeUnit timeUnit;
}
