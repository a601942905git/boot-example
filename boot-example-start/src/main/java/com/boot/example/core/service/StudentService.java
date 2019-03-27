package com.boot.example.core.service;

import com.boot.example.core.entity.StudentEntity;
import com.boot.example.core.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * com.boot.example.core.service.StudentService
 *
 * @author lipeng
 * @dateTime 2018/11/22 下午7:28
 */
@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public List<StudentEntity> listStudent() {
        return studentMapper.listStudent();
    }

    public StudentEntity getStudent(Integer id) {
        return studentMapper.getStudent(id);
    }

    public StudentEntity getStudentByName(String desc) {
        return studentMapper.getStudentByName(desc);
    }
}
