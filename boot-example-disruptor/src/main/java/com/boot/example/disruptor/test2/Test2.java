package com.boot.example.disruptor.test2;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;

/**
 * com.boot.example.disruptor.test2.Test2
 *
 * @author lipeng
 * @date 2018/12/26 下午4:58
 */
public class Test2 {

    public static void main(String[] args) throws InterruptedException {
        LongEventFactory longEventFactory = new LongEventFactory();
        int ringBufferSize = 1024;

        Disruptor<LongEvent> disruptor =
                new Disruptor<>(longEventFactory, ringBufferSize, DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        LongEventProducerWithTranslator producer =
                new LongEventProducerWithTranslator(ringBuffer);

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            bb.putLong(0, l);
            producer.onData(bb);
            Thread.sleep(1000);
        }
    }
}
