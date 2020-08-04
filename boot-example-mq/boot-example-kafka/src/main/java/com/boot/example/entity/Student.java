package com.boot.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * com.boot.example.entity.Student
 *
 * @author lipeng
 * @date 2020/2/20 下午10:06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {

    private Integer id;

    private String name;

    private Integer age;
}
