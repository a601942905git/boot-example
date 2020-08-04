package com.boot.example.core.model;

import lombok.*;

/**
 * com.boot.example.core.model.User
 *
 * @author lipeng
 * @date 2018/12/18 下午5:43
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    private Integer id;

    private String username;

    private String password;
}
