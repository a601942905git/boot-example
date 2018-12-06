package com.boot.example.annotation;

import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.boot.example.FlowRuleConfig;
import org.springframework.stereotype.Service;

/**
 * com.boot.example.annotation.SentinelAnnotationService
 *
 * @author lipeng
 * @dateTime 2018/12/5 上午10:21
 */
@Service
public class SentinelAnnotationService {

    public static final String RESOURCE_NAME = "hello";

    @SentinelResource(value = RESOURCE_NAME, blockHandler = "handlerException", fallback = "helloFallback")
    public String hello(String name) {
        FlowRuleConfig.initFlowRules("hello", 20);
        return "【sentinel】：hello " + name;
    }

    public String handlerException(String name, BlockException e) {
        System.out.println("【是否阻塞】：" + BlockException.isBlockException(e));
        Tracer.trace(e);
        return "【sentinel blockHandler】：hello " + name;
    }

    public String helloFallback(String name) {
        return "【sentinel fallback】：hello " + name;
    }
}
