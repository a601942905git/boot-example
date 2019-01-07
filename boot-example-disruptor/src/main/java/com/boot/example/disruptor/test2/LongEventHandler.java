package com.boot.example.disruptor.test2;


import com.lmax.disruptor.EventHandler;

/**
 * com.boot.example.disruptor.test2.LongEventHandler
 *
 * @author lipeng
 * @date 2018/12/26 下午4:50
 */
public class LongEventHandler implements EventHandler<LongEvent> {

    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("event value：" + event.getValue());
    }
}
