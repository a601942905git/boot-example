package com.boot.example.observe.custom;

import java.util.ArrayList;
import java.util.List;

/**
 * com.boot.example.observe.custom.Test
 *
 * @author lipeng
 * @date 2018/12/21 下午2:09
 */
public class Test {

    public static void main(String[] args) {
        List<CustomObserver> customObserveList = new ArrayList<>();

        CustomObserver1 customObserve1 = new CustomObserver1();
        CustomObserver2 customObserve2 = new CustomObserver2();
        CustomObserver3 customObserve3 = new CustomObserver3();

        customObserveList.add(customObserve1);
        customObserveList.add(customObserve2);
        customObserveList.add(customObserve3);

        CustomEvent customEvent = new CustomEvent(customObserveList);
        customEvent.hello();
    }
}
