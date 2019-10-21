package com.boot.example;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * com.boot.example.User
 *
 * @author lipeng
 * @dateTime 2018/12/11 下午5:47
 */
@Data
public class Teacher {

    @NotNull(message = "{id.not.empty}")
    private Integer id;

    @NotEmpty(message = "{name.not.empty}")
    private String name;

    @NotEmpty(message = "{email.not.empty}")
    @Email(message = "{email.invalid}")
    private String email;

    @Valid
    @NotEmpty(message = "{student.list.not.empty}")
    private List<Student> studentList;
}
