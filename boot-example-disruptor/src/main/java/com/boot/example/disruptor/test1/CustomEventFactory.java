package com.boot.example.disruptor.test1;

import com.lmax.disruptor.EventFactory;

/**
 * com.boot.example.disruptor.test1.CustomEventFactory
 *
 * @author lipeng
 * @date 2018/12/26 下午3:16
 */
public class CustomEventFactory implements EventFactory<Element> {

    @Override
    public Element newInstance() {
        return new Element();
    }
}
