package com.boot.example.disruptor.test3;

import com.boot.example.disruptor.test2.LongEvent;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * com.boot.example.disruptor.test3.Test3
 * 多消费者不重复消费
 * @author lipeng
 * @date 2019/1/2 下午4:18
 */
public class Test3 {

    public static void main(String[] args) throws InterruptedException {

        LongAdder longAdder = new LongAdder();

        ThreadFactory threadFactory = (r) -> {
            longAdder.increment();
            return new Thread(r, "multi-thread-" + longAdder.longValue());
        };

        EventFactory<LongEvent> eventFactory = LongEvent::new;

        final int ringBufferSize = 1024;

        Disruptor<LongEvent> disruptor =
                new Disruptor<LongEvent>(eventFactory, ringBufferSize, threadFactory,
                        ProducerType.SINGLE, new BlockingWaitStrategy());

        int size = 10;
        WorkHandler<LongEvent>[] workHandlers = new WorkHandler[size];
        for (int i = 0; i < size; i++) {
            workHandlers[i] = (event) -> {
                System.out.println("当前线程：" + Thread.currentThread().getName() + "输出结果：" + event.getValue());
            };
        }

        disruptor.handleEventsWithWorkerPool(workHandlers);
        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        for (Long i = 0L; i < 10; i++) {
            long sequence = ringBuffer.next();
            try {
                LongEvent longEvent = ringBuffer.get(sequence);
                longEvent.setValue(i);
            } finally {
                ringBuffer.publish(sequence);
            }
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }
}
