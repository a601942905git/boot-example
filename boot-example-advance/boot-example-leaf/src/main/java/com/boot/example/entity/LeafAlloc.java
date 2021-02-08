package com.boot.example.entity;

import lombok.Data;

/**
 * com.boot.example.entity.IdAllocate
 *
 * @author lipeng
 * @date 2021/2/8 11:15 AM
 */
@Data
public class LeafAlloc {

    private Long id;

    private String bizTag;

    private Long maxId;

    private Integer step;

    private String description;

    private String updateTime;
}
