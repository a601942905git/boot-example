package com.boot.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * com.boot.example.entity.Student
 *
 * @author lipeng
 * @date 2021/3/2 9:13 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student implements Serializable {

    private Integer id;

    private String name;

    private Integer age;
}
