package com.boot.example.disruptor.test2;

import com.lmax.disruptor.EventFactory;

/**
 * com.boot.example.disruptor.test2.LongEventFactory
 *
 * @author lipeng
 * @date 2018/12/26 下午4:50
 */
public class LongEventFactory implements EventFactory<LongEvent> {

    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
