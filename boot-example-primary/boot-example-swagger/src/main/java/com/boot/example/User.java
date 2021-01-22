package com.boot.example;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * com.boot.example.User
 *
 * @author lipeng
 * @dateTime 2018/12/13 上午11:26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @ApiModelProperty(value = "用户编号")
    private Integer id;

    @ApiModelProperty(value = "用户姓名")
    private String name;

}
