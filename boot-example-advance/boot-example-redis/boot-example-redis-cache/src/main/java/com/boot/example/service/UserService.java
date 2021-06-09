package com.boot.example.service;

import com.boot.example.annotation.Cache;
import com.boot.example.entity.Student;
import com.boot.example.request.UserListRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.UserService
 *
 * @author lipeng
 * @date 2021/6/2 11:44 AM
 */
@Service
@Slf4j
public class UserService {

    @Cache(name = "student_list", key = "#request.pageIndex + ':' + #request.pageSize", ttl = 600, randomTtl = 180)
    public List<Student> list(UserListRequest request) {
        log.info("查询数据库开始......");
        try {
            // 模拟查询数据库耗时
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(10001, "学生1"));
        studentList.add(new Student(10002, "学生2"));
        studentList.add(new Student(10003, "学生3"));
        log.info("查询数据库结束......");
        return studentList;
    }
}
