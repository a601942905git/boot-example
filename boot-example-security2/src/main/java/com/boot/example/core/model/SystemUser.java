package com.boot.example.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * com.boot.example.core.model.SystemUser
 *
 * @author lipeng
 * @dateTime 2018/12/14 下午2:35
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemUser {

    private Integer id;

    private String username;

    private String password;

    private List<SystemRole> roleList;
}
