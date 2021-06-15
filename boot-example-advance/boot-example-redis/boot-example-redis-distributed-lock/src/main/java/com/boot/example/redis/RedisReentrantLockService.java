package com.boot.example.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * com.boot.example.redis.RedisReentrantLockService
 *
 * @author lipeng
 * @date 2021/5/7 10:25 AM
 */
@Component
@Slf4j
public class RedisReentrantLockService {

    private final ThreadLocal<Map<String, Integer>> threadLocal = ThreadLocal.withInitial(HashMap::new);

    @Autowired
    private RedisLockService redisLockService;

    public Boolean lock(String key, long expireTime) {
        Map<String, Integer> map = threadLocal.get();
        Integer state = map.get(key);
        if (Objects.nonNull(state)) {
            log.info("get key：{} reentrant lock", key);
            map.put(key, state + 1);
            return true;
        }


        log.info("get key：{} lock", key);
        Boolean lockResult = redisLockService.lock(key, expireTime);
        if (lockResult) {
            map.put(key, 1);
        }

        return lockResult;
    }

    public Boolean unlock(String key) {
        Map<String, Integer> map = threadLocal.get();
        Integer state = map.get(key);
        if (Objects.isNull(state)) {
            return false;
        }

        int c = state - 1;
        if (c == 0) {
            log.info("unlock key：{} lock", key);
            Boolean unlockResult = redisLockService.unlock(key);
            if (unlockResult) {
                threadLocal.remove();
            }
            return unlockResult;
        }

        log.info("unlock key：{} reentrant lock", key);
        map.put(key, c);
        return false;
    }

}
