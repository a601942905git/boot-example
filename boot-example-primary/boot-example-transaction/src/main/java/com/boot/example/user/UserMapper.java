package com.boot.example.user;

import org.apache.ibatis.annotations.Mapper;

/**
 * com.boot.example.user.UserMapper
 *
 * @author lipeng
 * @date 2019-02-01 11:04
 */
@Mapper
public interface UserMapper {

    int saveUser(User user);
}
