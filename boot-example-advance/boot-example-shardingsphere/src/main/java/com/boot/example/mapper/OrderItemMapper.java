package com.boot.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.example.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lipeng
 * &#064;date 2023/12/7 13:52:09
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
}
