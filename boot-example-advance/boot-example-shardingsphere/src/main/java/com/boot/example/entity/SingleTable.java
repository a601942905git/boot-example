package com.boot.example.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lipeng
 * &#064;date 2023/11/30 19:46:47
 */
@TableName("single_table")
@Data
public class SingleTable {

    @TableId
    private Long id;

    private String key1;

    private Integer key2;

    private String key3;

    private String keyPart1;

    private String keyPart2;

    private String keyPart3;

    private String commonField;
}
