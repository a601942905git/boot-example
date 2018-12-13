package com.boot.example;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * com.boot.example.GreeterProperties
 *
 * @author lipeng
 * @dateTime 2018/12/13 下午4:56
 */
@ConfigurationProperties(prefix = "com.boot.example")
public class GreeterProperties {

    private String userName = "本机用户";

    private String morningMessage = "早上好";

    private String afternoonMessage = "中午好";

    private String eveningMessage = "下午好";

    private String nightMessage = "晚上好";

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMorningMessage() {
        return morningMessage;
    }

    public void setMorningMessage(String morningMessage) {
        this.morningMessage = morningMessage;
    }

    public String getAfternoonMessage() {
        return afternoonMessage;
    }

    public void setAfternoonMessage(String afternoonMessage) {
        this.afternoonMessage = afternoonMessage;
    }

    public String getEveningMessage() {
        return eveningMessage;
    }

    public void setEveningMessage(String eveningMessage) {
        this.eveningMessage = eveningMessage;
    }

    public String getNightMessage() {
        return nightMessage;
    }

    public void setNightMessage(String nightMessage) {
        this.nightMessage = nightMessage;
    }
}
