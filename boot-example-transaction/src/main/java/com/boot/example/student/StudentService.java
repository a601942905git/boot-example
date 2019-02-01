package com.boot.example.student;

import com.boot.example.user.User;
import com.boot.example.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * com.boot.example.student.StudentService
 *
 * @author lipeng
 * @date 2019-02-01 10:26
 */
@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private UserService userService;

    @Transactional(rollbackFor = Exception.class)
    public int saveStudent(Student student) {
        int result = this.studentMapper.saveStudent(student);
        System.out.println(1 / 0);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public int saveCommon(Student student, User user) {
        int result = studentMapper.saveStudent(student);
        userService.saveUser(user);
        System.out.println(1 / 0);
        return result;
    }
}
