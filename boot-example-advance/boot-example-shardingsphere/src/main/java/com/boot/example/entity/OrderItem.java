package com.boot.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lipeng
 * &#064;date 2023/12/7 11:51:03
 */
@TableName("t_order_item")
@Data
public class OrderItem {

    @TableId(type = IdType.AUTO)
    private Long orderItemId;

    private Long orderId;

    private Long userId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productQuantity;
}
