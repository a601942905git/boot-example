package com.boot.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * com.boot.example.Student
 *
 * @author lipeng
 * @date 2019/10/21 下午7:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private Integer id;

    private String name;

    private Integer age;
}
