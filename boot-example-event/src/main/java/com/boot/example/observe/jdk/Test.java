package com.boot.example.observe.jdk;

/**
 * com.boot.example.observe.jdk.Test
 *
 * @author lipeng
 * @date 2018/12/21 下午2:30
 */
public class Test {

    public static void main(String[] args) {
        SMSObserver smsObserver = new SMSObserver();
        PointObserver pointObserver = new PointObserver();

        RegisterEvent registerEvent = new RegisterEvent();
        registerEvent.addObserver(smsObserver);
        registerEvent.addObserver(pointObserver);

        registerEvent.register();
    }
}
