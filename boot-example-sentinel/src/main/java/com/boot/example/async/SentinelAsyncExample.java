package com.boot.example.async;

import com.alibaba.csp.sentinel.AsyncEntry;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * com.boot.example.async.SentinelAsyncExample
 *
 * @author lipeng
 * @dateTime 2018/12/5 下午1:17
 */
public class SentinelAsyncExample {

    public static final String RESOURCE_NAME = "sentinelAsync";

    public static void main(String[] args) {
        SentinelAsyncExample example = new SentinelAsyncExample();
        example.initFlowRule(RESOURCE_NAME, 20);
        while (true) {
            example.sync();
        }
    }

    private void invoke(Consumer handler) {
        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                handler.accept("asyncInvoke");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void async() {
        try {
            AsyncEntry asyncEntry = SphU.asyncEntry(RESOURCE_NAME);
            try {
                this.invoke((result) -> {
                    System.out.println("【异步调用回调结果】:" + result);
                });
            } finally {
                asyncEntry.exit();
            }
        } catch (BlockException e) {
            System.out.println("【资源调用阻塞】");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void sync() {
        try {
            Entry entry = SphU.entry(RESOURCE_NAME);
            try {
                this.invoke((result) -> {
                    System.out.println("【异步调用回调结果】:" + result);
                });
            } finally {
                entry.exit();
            }
        } catch (BlockException e) {
            System.out.println("【资源调用阻塞】");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void initFlowRule(String resourceName, int qpsCount) {
        List<FlowRule> flowRuleList = new ArrayList<>();

        FlowRule flowRule = new FlowRule();
        flowRule.setResource(resourceName);
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(qpsCount);

        flowRuleList.add(flowRule);

        FlowRuleManager.loadRules(flowRuleList);
    }
}
