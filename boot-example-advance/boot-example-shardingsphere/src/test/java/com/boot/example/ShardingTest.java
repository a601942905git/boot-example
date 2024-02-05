package com.boot.example;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.boot.example.entity.Order;
import com.boot.example.entity.OrderItem;
import com.boot.example.mapper.OrderItemMapper;
import com.boot.example.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author lipeng
 * &#064;date 2023/12/7 11:50:22
 */
@SpringBootTest(classes = ShardingSphereApplication.class)
public class ShardingTest {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Test
    public void testWrite() {
        Order order = new Order();
        order.setOrderId(null);
        order.setPrice(new BigDecimal(800));
        order.setUserId(10000003L);
        orderMapper.insert(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemId(null);
        orderItem.setOrderId(order.getOrderId());
        orderItem.setUserId(10000003L);
        orderItem.setProductName("test");
        orderItem.setProductPrice(new BigDecimal(400));
        orderItem.setProductQuantity(2);
        orderItemMapper.insert(orderItem);
    }

    @Test
    public void testRead() {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("user_id", 10000002L);
        orderQueryWrapper.eq("order_id", 939558556175171584L);
        List<Order> orderList = orderMapper.selectList(orderQueryWrapper);
        System.out.println(orderList);
    }
}
