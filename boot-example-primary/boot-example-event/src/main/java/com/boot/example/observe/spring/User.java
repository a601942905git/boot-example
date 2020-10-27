package com.boot.example.observe.spring;

import lombok.*;

/**
 * com.boot.example.observe.spring.User
 *
 * @author lipeng
 * @date 2018/12/21 下午3:04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    private Integer id;

    private String name;
}
