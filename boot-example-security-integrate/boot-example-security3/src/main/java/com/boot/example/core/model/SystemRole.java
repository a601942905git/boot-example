package com.boot.example.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * com.boot.example.core.model.SystemRole
 *
 * @author lipeng
 * @dateTime 2018/12/14 下午2:36
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemRole {

    private Integer id;

    private String name;

    private List<SystemPermission> permissionList;
}
