package com.boot.example.listener;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

/**
 * @author lipeng
 * &#064;date 2025/3/31 17:30:18
 */
@Component
@Slf4j
public class FlowExecutionTakeListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) {
        String eventName = execution.getEventName();
        String name = execution.getCurrentFlowElement().getName();
        log.info("event name:{}, name:{}", eventName, name);
    }
}
