package com.boot.example.smiple;

import com.alibaba.csp.sentinel.SphO;
import com.boot.example.FlowRuleConfig;

/**
 * com.boot.example.smiple.SentinelExample1
 * 基于返回布尔值的方式定义资源
 * @author lipeng
 * @dateTime 2018/12/5 上午9:43
 */
public class SentinelExample1 {

    public static final String RESOURCE_NAME = "HelloWorld";

    public static void main(String[] args) {
        FlowRuleConfig.initFlowRules(RESOURCE_NAME, 20);
        while (true) {
            if (SphO.entry(RESOURCE_NAME)) {
                try {
                    System.out.println("没有被限流，可以访问被保护的资源");
                } finally {
                    SphO.exit();
                }
            } else {
                // 资源访问阻止，被限流或被降级
                // 进行相应的处理操作
                System.out.println("被限流，不可以访问被保护的资源");
            }
        }
    }
}
