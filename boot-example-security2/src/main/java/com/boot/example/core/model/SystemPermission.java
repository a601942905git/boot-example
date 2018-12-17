package com.boot.example.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * com.boot.example.core.model.SystemPermission
 *
 * @author lipeng
 * @dateTime 2018/12/14 下午2:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemPermission {

    private Integer id;

    private String name;

    private String url;
}
