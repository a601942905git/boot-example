package com.boot.example.controller;

import com.boot.example.redis.RedisLockService;
import com.boot.example.request.RedisLockRequest;
import com.boot.example.request.RedisUnlockRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * com.boot.example.RedisController
 *
 * @author lipeng
 * @date 2021/2/2 10:52 AM
 */
@RestController
public class RedisLockController {

    @Autowired
    private RedisLockService redisService;

    @RequestMapping("/lock")
    public String lock(@RequestBody RedisLockRequest request) {
        Boolean lockFlag = redisService.lock(request.getKey(),
                request.getExpireTime(), request.getTimeUnit());
        if (Objects.equals(lockFlag, Boolean.TRUE)) {
            return "加锁成功";
        } else {
            return "加锁失败";
        }
    }

    @RequestMapping("/unlock")
    public Boolean lock(@RequestBody RedisUnlockRequest request) {
        return redisService.unlock(request.getKey());
    }
}
