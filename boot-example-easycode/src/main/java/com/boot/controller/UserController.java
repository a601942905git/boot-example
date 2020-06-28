package com.boot.controller;

import com.boot.entity.User;
import com.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * (User)表控制层
 *
 * @author lipeng
 * @since 2020-06-25 20:31:27
 */
@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @GetMapping("/id/{id}")
    User getById(@PathVariable("id") Integer id){
        return this.userService.getById(id);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param user 查询对象
     * @return 对象列表
     */
    @GetMapping("/")
    List<User> list(@RequestBody User user){
        return this.userService.list(user);
    }

    /**
     * 统计数量
     *
     * @param user 查询对象
     * @return 统计数量
     */
    @GetMapping("/count")
    Long count(@RequestBody User user){
        return this.userService.count(user);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 受影响行数
     */
    @PostMapping("/")
    int insert(@RequestBody User user){
        return this.userService.insert(user);
    }

    /**
     * 批量新增数据
     *
     * @param userList 实例对象列表
     * @return 受影响行数
     */
    @PostMapping("/batch/insert")
    int batchInsert(@RequestBody List<User> userList){
        return this.userService.batchInsert(userList);
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 受影响行数
     */
    @PutMapping("/")
    int update(@RequestBody User user){
        return this.userService.update(user);
    }
    
    /**
     * 批量修改数据
     *
     * @param idList 主键列表 
     * @return 受影响行数
     */
    @PutMapping("/batch/update")
    int batchUpdate(@RequestBody List<Integer> idList){
        return this.userService.batchUpdate(idList);
    }
}