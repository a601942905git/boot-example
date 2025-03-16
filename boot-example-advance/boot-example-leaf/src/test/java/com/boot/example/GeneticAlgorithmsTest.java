package com.boot.example;

import org.junit.jupiter.api.Test;

/**
 * 一个数字取余2的n次方，结果就是该数字二进制的后n位
 * @author lipeng
 * &#064;date 2024/12/25 10:53:22
 */
public class GeneticAlgorithmsTest {

    @Test
    public void test() {
        int num = 64;
        long userId = 34987893475894L;
        long orderId = 69457896739841L;
        // 用户id二进制字符串
        String userIdBinaryString = Long.toBinaryString(userId);
        System.out.println("用户id二进制字符串：" + userIdBinaryString);
        // userId & num - 1 等同于 user % num
        String v = Long.toBinaryString(userId & num - 1);
        System.out.println("v = " + v);
        long newOrderId = Long.parseLong(Long.toBinaryString(orderId) + v, 2);
        System.out.println("用户id取模结果：" + userId % num);
        System.out.println("原订单id取模结果：" + orderId % num);
        System.out.printf("新订单id：%s,取模结果：%s%n", newOrderId, newOrderId % num);
    }

    @Test
    public void test02() {
        String str = "1110100100100000011100001111110101110000001000000000110000000";
        System.out.println(str.length());
        System.out.println(Long.parseLong("1110100100100000011100001111110101110000001000000000110000000", 2));;
//        for (int i = 0; i < 1000; i++) {
//            long num = ((System.currentTimeMillis() - 1672531200000L) << 25) |
//                    (1 << 18) |
//                    (6 << 6);
//            long finalNum = Long.parseLong(Long.toBinaryString(num) + "110110", 2);
//            System.out.println(finalNum);
//        }
    }

}
