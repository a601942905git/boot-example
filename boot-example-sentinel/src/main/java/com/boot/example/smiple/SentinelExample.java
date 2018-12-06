package com.boot.example.smiple;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.boot.example.FlowRuleConfig;

import java.util.Objects;

/**
 * com.boot.example.smiple.SentinelExample
 * 基于异常的方式定义资源
 * @author lipeng
 * @dateTime 2018/12/4 下午2:59
 */
public class SentinelExample {

    public static final String RESOURCE_NAME = "HelloWorld";

    public static void main(String[] args) {
        FlowRuleConfig.initFlowRules(RESOURCE_NAME, 20);
        while (true) {
            Entry entry = null;
            try {
                entry = SphU.entry(RESOURCE_NAME);
                // 该部分为被保护的资源
                System.out.println("hello world");
            } catch (BlockException e) {
                // 此部分为资源访问受阻，进行被限流、被降级
                // 进行相应处理操作
                System.out.println("blocking");
                break;
            } finally {
                if (!Objects.isNull(entry)) {
                    entry.exit();
                }
            }
        }
    }
}
