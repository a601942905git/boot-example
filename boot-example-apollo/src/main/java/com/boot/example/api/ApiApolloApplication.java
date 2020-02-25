package com.boot.example.api;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * com.boot.example.api.ApiApolloApplication
 *
 * @author lipeng
 * @dateTime 2018/12/10 下午2:03
 */
@SpringBootApplication
public class ApiApolloApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApolloApplication.class, args);
        Config config = ConfigService.getAppConfig();
        System.out.println("timeout：" + config.getProperty("timeout", "100"));
        System.out.println("batch：" + config.getProperty("batch", "100"));

        Config dataSourceConfig = ConfigService.getConfig("order-service.dataSource");
        System.out.println("spring.datasource.url：" + dataSourceConfig.getProperty("spring.datasource.url", ""));

        System.out.println("================监听事件==================");
        config.addChangeListener(changeEvent -> {
            System.out.println("Changes for namespace " + changeEvent.getNamespace());
            for (String key : changeEvent.changedKeys()) {
                ConfigChange change = changeEvent.getChange(key);
                System.out.println(String.format("Found change - key: %s, oldValue: %s, newValue: %s, changeType: %s", change.getPropertyName(), change.getOldValue(), change.getNewValue(), change.getChangeType()));
            }
        });

    }
}
