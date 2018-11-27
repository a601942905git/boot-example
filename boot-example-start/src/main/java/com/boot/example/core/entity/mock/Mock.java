package com.boot.example.core.entity.mock;

import lombok.*;

/**
 * com.boot.example.core.entity.mock.Mock
 *
 * @author lipeng
 * @dateTime 2018/11/27 上午11:15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Mock {

    private Integer id;

    private String name;
}
