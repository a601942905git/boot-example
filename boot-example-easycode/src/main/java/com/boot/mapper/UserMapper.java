package com.boot.mapper;

import com.boot.entity.User;
import com.boot.entity.UserCondition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (User)表数据库访问层
 *
 * @author lipeng
 * @since 2020-06-26 09:10:43
 */
@Mapper
public interface UserMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User getById(Integer id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param userCondition 查询对象
     * @return 对象列表
     */
    List<User> list(UserCondition userCondition);

    /**
     * 统计数量
     *
     * @param userCondition 查询对象
     * @return 统计数量
     */
    Long count(UserCondition userCondition);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 受影响行数
     */
    int insert(User user);

    /**
     * 批量新增数据
     *
     * @param userList 实例对象列表
     * @return 受影响行数
     */
    int batchInsert(List<User> userList);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 受影响行数
     */
    int update(User user);
    
    /**
     * 批量修改数据
     *
     * @param userCondition 对象
     * @return 受影响行数
     */
    int batchUpdate(UserCondition userCondition);
}