package com.boot.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.example.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lipeng
 * &#064;date 2023/12/7 13:51:46
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
