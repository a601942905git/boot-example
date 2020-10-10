package com.boot.example;

import com.boot.example.factory.FactorySupportType;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * com.boot.example.OrderTypeEnum
 *
 * @author lipeng
 * @date 2019/11/7 下午5:15
 */
public enum OrderTypeEnum implements FactorySupportType {

    GROUP_ORDER(1, "拼团订单"),
    DISCOUNT_ORDER(2, "折扣订单"),
    PROMOTION_ORDER(3, "促销订单");

    OrderTypeEnum(Integer id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String desc;

    public static OrderTypeEnum getById(int id) {
        for (OrderTypeEnum orderTypeEnum : OrderTypeEnum.values()) {
            if (Objects.equals(orderTypeEnum.getId(), id)) {
                return orderTypeEnum;
            }
        }
        return null;
    }
}
