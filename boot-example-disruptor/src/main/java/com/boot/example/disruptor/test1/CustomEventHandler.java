package com.boot.example.disruptor.test1;

import com.lmax.disruptor.EventHandler;

/**
 * com.boot.example.disruptor.test1.CustomEventHandler
 *
 * @author lipeng
 * @date 2018/12/26 下午3:20
 */
public class CustomEventHandler implements EventHandler<Element> {

    @Override
    public void onEvent(Element element, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("Element: " + element.getValue());
    }
}
