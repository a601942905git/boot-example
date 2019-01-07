package com.boot.example.disruptor.test2;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * com.boot.example.disruptor.test2.LongEventProducerWithTranslator
 *
 * @author lipeng
 * @date 2018/12/26 下午4:52
 */
public class LongEventProducerWithTranslator {

    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducerWithTranslator(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    private static final EventTranslatorOneArg<LongEvent, ByteBuffer> TRANSLATOR_ONE_ARG
            = (event, sequence, buffer) -> event.setValue(buffer.getLong(0));

    public void onData(ByteBuffer buffer) {
        ringBuffer.publishEvent(TRANSLATOR_ONE_ARG, buffer);
    }
}
