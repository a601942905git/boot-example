package com.boot.example.student;

import org.apache.ibatis.annotations.Mapper;

/**
 * com.boot.example.student.StudentMapper
 *
 * @author lipeng
 * @date 2019-02-01 10:25
 */
@Mapper
public interface StudentMapper {

    int saveStudent(Student student);

    Student getStudentById(Integer id);

    int updateStudentAgeById(Student student);
}
