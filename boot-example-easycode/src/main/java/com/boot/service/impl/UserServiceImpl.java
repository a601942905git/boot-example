package com.boot.service.impl;

import com.boot.entity.User;
import com.boot.entity.UserCondition;
import com.boot.mapper.UserMapper;
import com.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (User)表服务实现类
 *
 * @author lipeng
 * @since 2020-06-25 20:30:17
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public User getById(Integer id){
        return this.userMapper.getById(id);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param user 查询对象
     * @return 对象列表
     */
    @Override
    public List<User> list(User user){
        UserCondition userCondition = new UserCondition();
        userCondition.createCriteria().andNameLike(user.getName() + "%");
        return this.userMapper.list(userCondition);
    }

    /**
     * 统计数量
     *
     * @param user 查询对象
     * @return 统计数量
     */
    @Override 
    public Long count(User user){
        UserCondition userCondition = new UserCondition();
        userCondition.createCriteria().andNameLike(user.getName() + "%");
        return this.userMapper.count(userCondition);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 受影响行数
     */
    @Override 
    public int insert(User user){
        return this.userMapper.insert(user);
    }

    /**
     * 批量新增数据
     *
     * @param userList 实例对象列表
     * @return 受影响行数
     */
    @Override
    public int batchInsert(List<User> userList){
        return this.userMapper.batchInsert(userList);
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 受影响行数
     */
    @Override
    public int update(User user){
        return this.userMapper.update(user);
    }
    
    /**
     * 批量修改数据
     *
     * @param idList 主键列表 
     * @return 受影响行数
     */
    @Override
    public int batchUpdate(List<Integer> idList){
        UserCondition userCondition = new UserCondition();
        userCondition.createCriteria().andAgeEqualTo(26).andIdIn(idList);
        return this.userMapper.batchUpdate(userCondition);
    }
}