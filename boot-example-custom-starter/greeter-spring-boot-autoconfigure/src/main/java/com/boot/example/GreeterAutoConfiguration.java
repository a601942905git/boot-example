package com.boot.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.boot.example.GreeterAutoConfiguration
 *
 * @author lipeng
 * @dateTime 2018/12/13 下午4:55
 */
@Configuration
@EnableConfigurationProperties(GreeterProperties.class)
@ConditionalOnClass(Greeter.class)
public class GreeterAutoConfiguration {

    @Autowired
    private GreeterProperties greeterProperties;

    @Bean
    @ConditionalOnMissingBean
    public GreeterConfig greeterConfig() {
        GreeterConfig greeterConfig = new GreeterConfig();

        String userName = greeterProperties.getUserName();
        String morningMessage = greeterProperties.getMorningMessage();
        String afternoonMessage = greeterProperties.getAfternoonMessage();
        String eveningMessage = greeterProperties.getEveningMessage();
        String nightMessage = greeterProperties.getNightMessage();

        greeterConfig.setUserName(userName);
        greeterConfig.setMorningMessage(morningMessage);
        greeterConfig.setAfternoonMessage(afternoonMessage);
        greeterConfig.setEveningMessage(eveningMessage);
        greeterConfig.setNightMessage(nightMessage);
        return greeterConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public Greeter greeter() {
        Greeter greeter = new Greeter(greeterConfig());
        return greeter;
    }
}
