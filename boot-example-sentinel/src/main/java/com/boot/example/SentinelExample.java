package com.boot.example;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * com.boot.example.SentinelExample
 *
 * @author lipeng
 * @dateTime 2018/12/4 下午2:59
 */
public class SentinelExample {

    public static void main(String[] args) {
        initFlowRules();
        while (true) {
            Entry entry = null;
            try {
                entry = SphU.entry("HelloWorld");
                System.out.println("hello world");
            } catch (BlockException e) {
                System.out.println("blocking");
            } finally {
                if (!Objects.isNull(entry)) {
                    entry.exit();
                }
            }
        }
    }

    public static void initFlowRules() {
        List<FlowRule> flowRuleList = new ArrayList<>();

        FlowRule flowRule = new FlowRule();
        flowRule.setResource("HelloWorld");
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(20);

        flowRuleList.add(flowRule);

        FlowRuleManager.loadRules(flowRuleList);
    }
}
