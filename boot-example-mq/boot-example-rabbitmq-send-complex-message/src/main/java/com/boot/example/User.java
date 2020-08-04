package com.boot.example;

import lombok.*;

/**
 * com.boot.example.User
 *
 * @author lipeng
 * @date 2019-01-21 22:56
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
