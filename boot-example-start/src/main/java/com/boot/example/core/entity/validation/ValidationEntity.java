package com.boot.example.core.entity.validation;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * com.boot.example.core.entity.validation.ValidationEntity
 *
 * @author lipeng
 * @dateTime 2018/11/24 下午2:51
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ValidationEntity {

    @NotNull(message = "编号不能为空")
    private Integer id;

    @NotNull(message = "姓名不能为空")
    @NotEmpty(message = "姓名不能为空")
    private String name;

    @NotNull(message = "电子邮箱不能为空")
    @NotEmpty(message = "电子邮箱不能为空")
    @Email(message = "电子邮箱格式不正确")
    private String email;
}
