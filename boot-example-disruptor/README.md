# 示例代码
```
public class Test1 {

    public static void main(String[] args) throws InterruptedException {
        ThreadFactory threadFactory = r -> new Thread(r, "disruptor-thread");

        BlockingWaitStrategy blockingWaitStrategy = new BlockingWaitStrategy();

        int ringBufferSize = 16;

        // 1
        Disruptor<Element> disruptor = new Disruptor<>(new CustomEventFactory(), ringBufferSize,
                threadFactory, ProducerType.SINGLE, blockingWaitStrategy);
    
        // 2
        disruptor.handleEventsWith(new CustomEventHandler());
        
        // 3
        disruptor.start();

        RingBuffer<Element> ringBuffer = disruptor.getRingBuffer();

        for (int i = 0; true; i++) {
            long sequence = ringBuffer.next();
            try {
                Element element = ringBuffer.get(sequence);
                element.setValue(i);
            } finally {
                // 4
                ringBuffer.publish(sequence);
            }
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }
}
```
如上标记了四个阶段代码，下来来逐一进行分析

# 第一阶段
第一阶段代码一眼就可以看出主要用来创建Disruptor对象，那么里面具体还做了哪些事情呢？
```
public Disruptor(
        final EventFactory<T> eventFactory,
        final int ringBufferSize,
        final ThreadFactory threadFactory,
        final ProducerType producerType,
        final WaitStrategy waitStrategy) {
    this(
        RingBuffer.create(producerType, eventFactory, ringBufferSize, waitStrategy),
        new BasicExecutor(threadFactory));
}
```
首先创建了RingBuffer对象，我们继续看看RingBuffer的create()方法
```
public static <E> RingBuffer<E> create(
        ProducerType producerType,
        EventFactory<E> factory,
        int bufferSize,
        WaitStrategy waitStrategy) {
    switch (producerType) {
        case SINGLE:
            return createSingleProducer(factory, bufferSize, waitStrategy);
        case MULTI:
            return createMultiProducer(factory, bufferSize, waitStrategy);
        default:
            throw new IllegalStateException(producerType.toString());
    }
}
```
由于Disruptor提供了单生产和多生产类型，所以此处会根据我们传入的类型来创建具体RingBuffer对象，这里我们拿单生产类型为例
```
public static <E> RingBuffer<E> createSingleProducer(
        EventFactory<E> factory,
        int bufferSize,
        WaitStrategy waitStrategy) {
    SingleProducerSequencer sequencer = new SingleProducerSequencer(bufferSize, waitStrategy);

    return new RingBuffer<E>(factory, sequencer);
}
```
首先看看SingleProducerSequencer创建过程
```
SingleProducerSequencer sequencer = new SingleProducerSequencer(bufferSize, waitStrategy);
```
如上代码我们一直跟进去会是下面这样的
```
public AbstractSequencer(int bufferSize, WaitStrategy waitStrategy) {
    if (bufferSize < 1) {
        throw new IllegalArgumentException("bufferSize must not be less than 1");
    }
    if (Integer.bitCount(bufferSize) != 1) {
        throw new IllegalArgumentException("bufferSize must be a power of 2");
    }

    this.bufferSize = bufferSize;
    this.waitStrategy = waitStrategy;
}
```
主要给AbstractSequencer类中的bufferSize、waitStrategy赋值。回到刚才new RingBuffer创建过程
```
RingBufferFields(
        EventFactory<E> eventFactory,
        Sequencer sequencer) {
    this.sequencer = sequencer;
    this.bufferSize = sequencer.getBufferSize();

    if (bufferSize < 1) {
        throw new IllegalArgumentException("bufferSize must not be less than 1");
    }
    if (Integer.bitCount(bufferSize) != 1) {
        throw new IllegalArgumentException("bufferSize must be a power of 2");
    }

    this.indexMask = bufferSize - 1;
    this.entries = new Object[sequencer.getBufferSize() + 2 * BUFFER_PAD];
    fill(eventFactory);
}

private void fill(EventFactory<E> eventFactory) {
    for (int i = 0; i < bufferSize; i++) {
        entries[BUFFER_PAD + i] = eventFactory.newInstance();
    }
}
```
主要做了下面几件事情：
1. 将我们刚才创建的SingleProducerSequencer赋值给了sequencer
2. 给bufferSize赋值
3. 创建数组对象，调用eventFactory#newInstance()给数组赋值

然后我们回到Disruptor的构造中，调用了如下代码
```
private Disruptor(final RingBuffer<T> ringBuffer, final Executor executor) {
    this.ringBuffer = ringBuffer;
    this.executor = executor;
}
```
很简单，就是给Disruptor对象的ringBuffer、executor属性赋值

# 第一阶段总结
1. 创建SingleProducerSequencer对象
2. 创建RingBuffer对象，初始化数组
3. 创建Disruptor对象

# 第二阶段
第二阶段就是设置事件处理器，也就是我们的消费者
```
EventHandlerGroup<T> createEventProcessors(
        final Sequence[] barrierSequences,
        final EventHandler<? super T>[] eventHandlers) {
    checkNotStarted();

    final Sequence[] processorSequences = new Sequence[eventHandlers.length];
    final SequenceBarrier barrier = ringBuffer.newBarrier(barrierSequences);

    for (int i = 0, eventHandlersLength = eventHandlers.length; i < eventHandlersLength; i++)
    {
        final EventHandler<? super T> eventHandler = eventHandlers[i];

        final BatchEventProcessor<T> batchEventProcessor =
            new BatchEventProcessor<>(ringBuffer, barrier, eventHandler);

        if (exceptionHandler != null)
        {
            batchEventProcessor.setExceptionHandler(exceptionHandler);
        }

        consumerRepository.add(batchEventProcessor, eventHandler, barrier);
        processorSequences[i] = batchEventProcessor.getSequence();
    }

    updateGatingSequencesForNextInChain(barrierSequences, processorSequences);

    return new EventHandlerGroup<>(this, consumerRepository, processorSequences);
}
```
接下来会逐行分下上面的代码
```
final SequenceBarrier barrier = ringBuffer.newBarrier(barrierSequences);
```
这行代码创建了一个序列屏障，主要用来当没有可以消费的事件时用来阻塞消费者，我们点进newBarrier里面继续查看
```
@Override
    public SequenceBarrier newBarrier(Sequence... sequencesToTrack) {
    return new ProcessingSequenceBarrier(this, waitStrategy, cursor, sequencesToTrack);
}
```
最终调用了AbstractSequencer#newBarrier()方法，把当前对象中的序号指针赋值给了ProcessingSequenceBarrier对象的cursorSequence属性，这点很关键，后面生产者publish的时候改变该属性的值

回到刚才的方法，接下来看看for循环中的代码，可以看到把我们定义的时间处理器封装成了一个BatchEventProcessor对象，然后添加到了ConsumerRepository对象的集合中

# 第三阶段
第三阶段的代码就是启动Disruptor
```
// 3
disruptor.start();
```
接下来我们看看start()里面做了什么事情
```
public RingBuffer<T> start() {
    checkOnlyStartedOnce();
    for (final ConsumerInfo consumerInfo : consumerRepository)
    {
        consumerInfo.start(executor);
    }

    return ringBuffer;
}
```
循环启动我们的消费者，由第二阶段我们知道这里的ConsumerInfo实际上就是EventProcessorInfo，那我们来看看EventProcessorInfo#start()方法
```
@Override
public void start(final Executor executor) {
    executor.execute(eventprocessor);
}
```
eventprocessor其实就是BatchEventProcessor，接下来看看该类的run()方法
```
processEvents();
```
主要看下该方法的实现
```
private void processEvents() {
    T event = null;
    long nextSequence = sequence.get() + 1L;

    while (true) {
        try {
            final long availableSequence = sequenceBarrier.waitFor(nextSequence);
            if (batchStartAware != null) {
                batchStartAware.onBatchStart(availableSequence - nextSequence + 1);
            }

            while (nextSequence <= availableSequence) {
                event = dataProvider.get(nextSequence);
                eventHandler.onEvent(event, nextSequence, nextSequence == availableSequence);
                nextSequence++;
            }

            sequence.set(availableSequence);
        }
        catch (final TimeoutException e) {
            notifyTimeout(sequence.get());
        }
        catch (final AlertException ex) {
            if (running.get() != RUNNING) {
                break;
            }
        }
        catch (final Throwable ex) {
            exceptionHandler.handleEventException(ex, nextSequence, event);
            sequence.set(nextSequence);
            nextSequence++;
        }
    }
}
```
首先消费者先对自己持有的sequence + 1，消费数组中的第一个事件

在访问之前会被序列屏障阻塞，相当于消费者在消费之前需要先进行申请，如果可以消费，返回可以消费的序列号
```
final long availableSequence = sequenceBarrier.waitFor(nextSequence);
```
我们点进去看看
```
@Override
public long waitFor(final long sequence)
    throws AlertException, InterruptedException, TimeoutException {
    checkAlert();

    long availableSequence = waitStrategy.waitFor(sequence, cursorSequence, dependentSequence, this);

    if (availableSequence < sequence) {
        return availableSequence;
    }

    return sequencer.getHighestPublishedSequence(sequence, availableSequence);
}
```
重点看下下面这段代码
```
long availableSequence = waitStrategy.waitFor(sequence, cursorSequence, dependentSequence, this);
```
调用我们指定等待策略的waitFor()方法，再点进去看看
```
@Override
    public long waitFor(long sequence, Sequence cursorSequence, Sequence dependentSequence, SequenceBarrier barrier)
        throws AlertException, InterruptedException {
    long availableSequence;
    if (cursorSequence.get() < sequence) {
        lock.lock();
        try {
            while (cursorSequence.get() < sequence) {
                barrier.checkAlert();
                processorNotifyCondition.await();
            }
        }
        finally {
            lock.unlock();
        }
    }

    while ((availableSequence = dependentSequence.get()) < sequence) {
        barrier.checkAlert();
        ThreadHints.onSpinWait();
    }

    return availableSequence;
}
```
此段代码就是如果当前生产指针对应的序列号小于消费者申请的序列号，表明没有可以消费的事件，那么消费者就应该阻塞，那么消费者在什么时候被唤醒呢？看接下来的第四阶段

# 第四阶段
```
@Override
public void publish(long sequence) {
    cursor.set(sequence);
    waitStrategy.signalAllWhenBlocking();
}
```
会修改生产者对应的指针，cursor点进去我们可以看到该变量就是
AbstractSequencer类的一个属性，之前我们在创建序列屏障的时候使用到了该值，在当前类中搜索newBarrier
```
@Override
public SequenceBarrier newBarrier(Sequence... sequencesToTrack) {
    return new ProcessingSequenceBarrier(this, waitStrategy, cursor, sequencesToTrack);
}
```
由于我们传递的是引用，所以会发生联动效应，即在publish更新生产指针的时候ProcessingSequenceBarrier类的cursorSequence也会跟着变动，在看看ProcessingSequenceBarrier#waitFor()
```
long availableSequence = waitStrategy.waitFor(sequence, cursorSequence, dependentSequence, this);
```
我们又把该序列之传递给力我们指定的阻塞策略中，在来看看BlockingWaitStrategy#waitFor()方法
```
while (cursorSequence.get() < sequence) {
    barrier.checkAlert();
    processorNotifyCondition.await();
}
```
消费者会在这里一直循环，直到生产者发布时间，是的条件不满足，结束循环。结束循环后会返回可以访问的最大的序列号

接下来再回到BatchEventProcessor#processEvents()方法，也就是消费者消费的代码
```
final long availableSequence = sequenceBarrier.waitFor(nextSequence);
if (batchStartAware != null) {
    batchStartAware.onBatchStart(availableSequence - nextSequence + 1);
}

while (nextSequence <= availableSequence) {
    event = dataProvider.get(nextSequence);
    eventHandler.onEvent(event, nextSequence, nextSequence == availableSequence);
    nextSequence++;
}

sequence.set(availableSequence);
```
首先我们通过阻塞的方式获取到可以消费的最大序列号，然后从当前序列号一直循环消费，知道最大的序列号，最终调用sequence.set(availableSequence)方法来标记当前消费者消费到的位置。

# 总结
其实最主要的就是AbstractSequencer中的cursor属性，用来连接生产者和消费者

# 参考文献
- [Disruptor源码解读](https://www.cnblogs.com/stevenczp/p/7783977.html)