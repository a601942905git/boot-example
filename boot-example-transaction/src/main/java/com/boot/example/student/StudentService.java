package com.boot.example.student;

import com.boot.example.user.User;
import com.boot.example.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

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

    public Integer addStudentByManualTransaction(Student student) {
        Integer result;
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            result = studentMapper.saveStudent(student);
            platformTransactionManager.commit(transactionStatus);
        } catch (Exception e) {
            platformTransactionManager.rollback(transactionStatus);
            throw e;
        }
        return result;
    }
}
