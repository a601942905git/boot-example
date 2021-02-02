package com.boot.example.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.RedisUtils
 *
 * @author lipeng
 * @date 2021/2/1 5:20 PM
 */
@Component
public class RedisService {

    private final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    private final RedisScript<Long> redisScript = RedisScript.of(new ClassPathResource("del_key.lua"), Long.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 加锁存在的异常场景：
     * 1.加锁成功，在执行锁删除逻辑前机器宕机，解决方案：设置过期时间
     * 2.加锁成功，在设置锁超时时间前机器宕机，解决方案：使用原子操作进行加锁 + 设置过期时间 SET KEY VALUE NX EX max-lock-time
     *
     * 加锁使用 SET resource-name any-string NX EX max-lock-time
     *
     * @param key 加锁key
     * @param expireTime 过期时间
     * @param timeUnit 时间单位
     * @return true：加锁成功 false：加锁失败
     */
    public Boolean lock(String key, long expireTime, TimeUnit timeUnit) {
        String lockValue = UUID.randomUUID().toString();
        setLockValue(lockValue);
        return stringRedisTemplate.opsForValue().setIfAbsent(key, lockValue, expireTime, timeUnit);
    }

    /**
     * 解锁
     * 使用lua脚本解锁
     * 防止A用户成功加锁，锁超时后自动删除，B用户成功加锁，此时A用户使用删除逻辑删除B用户的锁
     *
     * @param key 解锁key
     * @return 返回1：解锁成功 返回0：解锁失败
     */
    public Long unlock(String key) {
        String lockValue = threadLocal.get();
        return stringRedisTemplate.execute(redisScript, Collections.singletonList(key), lockValue);
    }

    private void setLockValue(String lockValue) {
        threadLocal.set(lockValue);
    }
}
