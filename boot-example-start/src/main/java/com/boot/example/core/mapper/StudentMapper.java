package com.boot.example.core.mapper;

import com.boot.example.core.entity.StudentEntity;

import java.util.List;

/**
 * com.boot.example.core.mapper.StudentMapper
 *
 * @author lipeng
 * @dateTime 2018/11/22 下午7:28
 */
public interface StudentMapper {

    List<StudentEntity> listStudent();
}
