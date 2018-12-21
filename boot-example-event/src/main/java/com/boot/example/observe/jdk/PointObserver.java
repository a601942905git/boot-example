package com.boot.example.observe.jdk;

import java.util.Observable;
import java.util.Observer;

/**
 * com.boot.example.observe.jdk.PointObserver
 *
 * @author lipeng
 * @date 2018/12/21 下午2:28
 */
public class PointObserver implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("【赠送积分】");
    }
}
