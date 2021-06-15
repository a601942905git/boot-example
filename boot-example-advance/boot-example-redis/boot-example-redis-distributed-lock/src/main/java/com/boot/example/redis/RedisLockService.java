package com.boot.example.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.RedisUtils
 *
 * @author lipeng
 * @date 2021/2/1 5:20 PM
 */
@Component
@Slf4j
public class RedisLockService {

    private final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    private final RedisScript<Long> redisScript = RedisScript.of(new ClassPathResource("del_key.lua"), Long.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 加锁存在的异常场景：
     * 1.加锁成功，在执行锁删除逻辑前机器宕机，解决方案：设置过期时间
     * 2.加锁成功，在设置锁过期时间前机器宕机，解决方案：使用原子操作进行加锁 + 设置过期时间 SET KEY VALUE NX EX max-lock-time
     *
     * 加锁使用 SET resource-name any-string NX EX max-lock-time
     *
     * @param key 加锁key
     * @param expireTime 过期时间
     * @return true：加锁成功 false：加锁失败
     */
    public Boolean lock(String key, long expireTime) {
        String lockValue = UUID.randomUUID().toString();
        setLockValue(lockValue);
        return stringRedisTemplate.opsForValue().setIfAbsent(key, lockValue, expireTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 加锁
     *
     * @param key 加锁key
     * @param expireTime 过期时间，单位ms
     * @param waitTime 获取锁等待时间，单位ms
     * @return true：加锁成功 false：加锁失败
     */
    public Boolean lock(String key, long expireTime, long waitTime) {
        String lockValue = UUID.randomUUID().toString();
        setLockValue(lockValue);

        // 总休眠时间
        long totalSleepTime = 0L;
        // 每次休眠时间
        long sleepTimeEachTime;
        while (totalSleepTime < waitTime) {
            // 执行加锁
            Boolean lockResult = stringRedisTemplate.opsForValue()
                    .setIfAbsent(key, lockValue, expireTime, TimeUnit.MILLISECONDS);
            // 加锁成功，直接返回
            if (Objects.equals(Boolean.TRUE, lockResult)) {
                log.info("get {} redis lock success", key);
                return Boolean.TRUE;
            }

            // 当前key剩余过期时间
            Long pTtl = stringRedisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
            if (Objects.isNull(pTtl)) {
                log.warn("get redis lock fail，key：{} pttl return null", key);
                return false;
            }

            // 当前key已经过期
            if (pTtl == -2) {
                continue;
            }

            // 过期时间小于等待时间
            if (pTtl <= waitTime) {
                sleepTimeEachTime = pTtl;
                totalSleepTime += sleepTimeEachTime;
                try {
                    TimeUnit.MILLISECONDS.sleep(sleepTimeEachTime);
                } catch (InterruptedException e) {
                    log.warn("get redis lock interrupted exception：", e);
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("get redis lock interrupted exception");
                }
                continue;
            }
            break;
        }

        log.warn("get {} redis lock fail", key);
        return false;
    }

    /**
     * 解锁
     * 使用lua脚本解锁
     * 防止A用户成功加锁，锁超时后自动删除，B用户成功加锁，此时A用户使用删除逻辑删除B用户的锁
     *
     * @param key 解锁key
     * @return 返回1：解锁成功 返回0：解锁失败
     */
    public Boolean unlock(String key) {
        String lockValue = getLockValue();
        Long executeResult = stringRedisTemplate.execute(redisScript, Collections.singletonList(key), lockValue);
        removeLockValue();
        if (Objects.equals(executeResult, 1L)) {
            log.info("release {} redis lock success", key);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private void setLockValue(String lockValue) {
        threadLocal.set(lockValue);
    }

    private String getLockValue() {
        return threadLocal.get();
    }

    private void removeLockValue() {
        threadLocal.remove();
    }
}
