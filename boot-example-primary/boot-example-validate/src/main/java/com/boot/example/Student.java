package com.boot.example;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * com.boot.example.Student
 *
 * @author lipeng
 * @date 2019/10/21 下午9:05
 */
@Data
public class Student {

    @NotNull(message = "{student.id.not.empty}")
    private Integer id;

    @NotBlank(message = "{student.name.not.empty}")
    private String name;
}
