package com.boot.example.disruptor.test1;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.disruptor.test1.Test1
 *
 * @author lipeng
 * @date 2018/12/26 下午3:21
 */
public class Test1 {

    public static void main(String[] args) throws InterruptedException {
        ThreadFactory threadFactory = r -> new Thread(r, "disruptor-thread");

        BlockingWaitStrategy blockingWaitStrategy = new BlockingWaitStrategy();

        int ringBufferSize = 16;

        Disruptor<Element> disruptor = new Disruptor<>(new CustomEventFactory(), ringBufferSize,
                threadFactory, ProducerType.SINGLE, blockingWaitStrategy);
        disruptor.handleEventsWith(new CustomEventHandler());
        disruptor.start();

        RingBuffer<Element> ringBuffer = disruptor.getRingBuffer();

        for (int i = 0; true; i++) {
            long sequence = ringBuffer.next();
            try {
                Element element = ringBuffer.get(sequence);
                element.setValue(i);
            } finally {
                ringBuffer.publish(sequence);
            }
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }
}
