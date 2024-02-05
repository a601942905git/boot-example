package com.boot.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.MybatisParameterHandler;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import lombok.Data;
import org.apache.ibatis.reflection.MetaObject;

import java.math.BigDecimal;

/**
 * @see MybatisParameterHandler#populateKeys(TableInfo, MetaObject, Object)
 *
 * @author lipeng
 * &#064;date 2023/12/7 11:51:03
 */
@TableName("t_order")
@Data
public class Order {

    @TableId(type = IdType.AUTO)
    private Long orderId;

    private Long userId;

    private BigDecimal price;
}
