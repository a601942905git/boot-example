package com.boot.example.observe.custom;

import java.util.List;

/**
 * com.boot.example.observe.custom.CustomEvent
 *
 * @author lipeng
 * @date 2018/12/21 下午1:52
 */
public class CustomEvent {

    private List<CustomObserver> customObserveList;

    public CustomEvent(List<CustomObserver> customObserveList) {
        this.customObserveList = customObserveList;
    }

    public void hello() {
        if (null != customObserveList && customObserveList.size() > 0) {
            customObserveList.forEach(CustomObserver::hello);
        }
    }
}
