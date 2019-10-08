package com.boot.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * com.boot.example.Person
 *
 * @author lipeng
 * @date 2019/9/23 下午3:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @NotNull(message = "id can not be null")
    private Integer id;

    @NotEmpty(message = "name can not be null")
    private String name;
}
