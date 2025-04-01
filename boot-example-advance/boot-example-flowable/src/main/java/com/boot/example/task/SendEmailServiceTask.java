package com.boot.example.task;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.Expression;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

/**
 * @author lipeng
 * &#064;date 2025/4/1 13:41:56
 */
@Component
@Slf4j
public class SendEmailServiceTask implements JavaDelegate {

    private Expression email;

    @Override
    public void execute(DelegateExecution execution) {
        log.info("Send email to: {}", email.getValue(execution));
    }
}
