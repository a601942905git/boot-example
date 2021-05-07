package com.boot.example.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * com.boot.example.request.RedisUnlockRequest
 *
 * @author lipeng
 * @date 2021/2/2 10:57 AM
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RedisUnlockRequest {

    private String key;
}
