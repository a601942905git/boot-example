package com.boot.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * com.boot.example.entity.User
 *
 * @author lipeng
 * @dateTime 2018/11/30 下午2:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {

    private Integer id;

    private String name;
}
