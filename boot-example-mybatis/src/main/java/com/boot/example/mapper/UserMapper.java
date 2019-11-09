package com.boot.example.mapper;

import com.boot.example.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * com.boot.example.UserMapper
 *
 * @author lipeng
 * @date 2019/11/9 下午9:48
 */
@Mapper
public interface UserMapper {

    /**
     * 查询用户列表信息
     * @return 用户列表信息
     */
    List<User> list();

    /**
     * 根据编号查询用户信息
     * @param id 用户id
     * @return 用户信息
     */
    User getById(Integer id);

    /**
     * 新增用户
     * @param user 用户信息
     * @return 受影响的行数
     */
    int save(User user);

    /**
     * 修改用户
     * @param user 用户信息
     * @return 受影响的行数
     */
    int update(User user);
}
