package com.boot.example;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * com.boot.example.FlowRuleConfig
 *
 * @author lipeng
 * @dateTime 2018/12/5 上午10:31
 */
public class FlowRuleConfig {

    /**
     * 定义限流规则
     * @param resourceName  资源名称
     * @param qpsCount      qps
     */
    public static void initFlowRules(String resourceName, Integer qpsCount) {
        List<FlowRule> flowRuleList = new ArrayList<>();

        FlowRule flowRule = new FlowRule();
        flowRule.setResource(resourceName);
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(qpsCount);

        flowRuleList.add(flowRule);

        FlowRuleManager.loadRules(flowRuleList);
    }
}
