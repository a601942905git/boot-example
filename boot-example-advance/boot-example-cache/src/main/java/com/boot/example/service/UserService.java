package com.boot.example.service;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.boot.example.entity.User;
import com.boot.example.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * com.boot.example.service.UserService
 *
 * @author lipeng
 * @date 2021/3/9 5:14 PM
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Cached(name = "userCache-", expire = 3600)
    public List<User> list() {
        log.info("<<<execute list method>>>");
        return userMapper.list();
    }

    @Cached(name = "userCache-", key = "#id", expire = 3600)
    public User getById(Integer id) {
        log.info("<<<execute getById method>>>");
        return userMapper.getById(id);
    }

    @CacheInvalidate(name = "userCache-")
    public void save(User user) {
        log.info("<<<execute save method>>>");
        userMapper.save(user);
    }

    @CacheUpdate(name = "userCache-", key = "#user.id", value = "#user")
    public void update(User user) {
        log.info("<<<execute update method>>>");
        userMapper.update(user);
    }

    @CacheInvalidate(name = "userCache-", key = "#id")
    public void delete(Integer id) {
        log.info("<<<execute delete method>>>");
        userMapper.delete(id);
    }
}
