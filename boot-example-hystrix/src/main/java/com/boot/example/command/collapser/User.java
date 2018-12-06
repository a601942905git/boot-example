package com.boot.example.command.collapser;

import lombok.*;

/**
 * com.boot.example.command.collapser.User
 *
 * @author lipeng
 * @dateTime 2018/12/6 下午3:19
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
