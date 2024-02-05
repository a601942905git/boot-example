package com.boot.example.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * com.boot.example.entity.User
 *
 * @author lipeng
 * @date 2020/11/3 10:47 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @NotNull(message = "id can not be null")
    private Integer id;

    @NotBlank(message = "name can not be blank")
    private String name;

    private Integer age;
}
