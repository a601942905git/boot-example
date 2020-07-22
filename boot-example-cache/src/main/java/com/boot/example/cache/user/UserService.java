package com.boot.example.cache.user;

import com.boot.example.entity.User;

import java.net.UnknownHostException;

/**
 * com.boot.example.cache.user.UserService
 *
 * @author lipeng
 * @dateTime 2018/11/30 下午3:16
 */
public interface UserService {

    @Cached(name = "UserService.getUserFromMethodCache", expire = 100, cacheType = CacheType.BOTH, key = "#id")
    User getUserFromMethodCache(Integer id);

    User getUser(Integer id);

    @CacheUpdate(name = "UserService.getUserFromMethodCache", key = "#user.id", value = "#user")
    void updateUser(User user);

    @CacheInvalidate(name = "UserService.getUserFromMethodCache", key = "#id")
    void remove(Integer id);

    @Cached(name = "UserService.getUserFromRefreshCache", expire = 10, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 2, stopRefreshAfterLastAccess = 100)
    User getUserFromRefreshCache() throws UnknownHostException;
}
