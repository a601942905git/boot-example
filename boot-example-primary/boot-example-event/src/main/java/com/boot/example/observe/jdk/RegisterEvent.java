package com.boot.example.observe.jdk;

import java.util.Observable;

/**
 * com.boot.example.observe.jdk.RegisterEvent
 *
 * @author lipeng
 * @date 2018/12/21 下午2:28
 */
public class RegisterEvent extends Observable {

    public void register() {
        System.out.println("【用户注册成功】");
        setChanged();
        notifyObservers();
    }
}
