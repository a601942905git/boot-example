package com.boot.example.cache.user;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.boot.example.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * com.boot.example.cache.user.UserService
 *
 * @author lipeng
 * @dateTime 2018/11/30 下午2:21
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @CreateCache(name = "UserService.getUser", expire = 100, cacheType = CacheType.BOTH)
    private Cache<Integer, User> userCache;

    @Override
    public User getUser(Integer id) {
        User user = userCache.get(id);
        if (Objects.isNull(user)) {
            log.info("<<<缓存中不存在>>>");
            User user1 = User.builder()
                    .id(10001)
                    .name("测试")
                    .build();
            userCache.put(id, user1);
            return user1;
        }
        log.info("<<<从缓存中获取>>>");
        return user;
    }

    @Override
    public User getUserFromMethodCache(Integer id) {
        return User.builder()
                .id(10002)
                .name("test cache method")
                .build();
    }

    @Override
    public User getUserFromRefreshCache() {
        return User.builder()
                .id(10003)
                .name("test refersh cache")
                .build();
    }

    @Override
    public void updateUser(User user) {
        System.out.println("修改用户信息");
    }

    @Override
    public void remove(Integer id) {
        System.out.println("删除用户信息");
    }
}
