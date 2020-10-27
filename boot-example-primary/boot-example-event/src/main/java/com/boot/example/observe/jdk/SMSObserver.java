package com.boot.example.observe.jdk;

import java.util.Observable;
import java.util.Observer;

/**
 * com.boot.example.observe.jdk.SMSObserver
 *
 * @author lipeng
 * @date 2018/12/21 下午2:26
 */
public class SMSObserver implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("【发送短信】");
    }
}
