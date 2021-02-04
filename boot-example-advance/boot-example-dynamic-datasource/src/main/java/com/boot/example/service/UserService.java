package com.boot.example.service;

import com.boot.example.datasource.DynamicDataSource;
import com.boot.example.datasource.DynamicDataSourceConstants;
import com.boot.example.entity.User;
import com.boot.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * com.boot.example.service.UserService
 *
 * @author lipeng
 * @date 2021/2/4 10:23 AM
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @DynamicDataSource()
    public List<User> listMasterUser() {
        return userMapper.list();
    }

    public List<User> listUser() {
        return userMapper.list();
    }

    @DynamicDataSource(DynamicDataSourceConstants.DYNAMIC_DATA_SOURCE_KEY_SLAVE)
    public List<User> listSlaveUser() {
        return userMapper.list();
    }
}
