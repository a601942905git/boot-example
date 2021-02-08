package com.boot.example.service;

import com.boot.example.common.Result;
import com.boot.example.common.Status;
import com.boot.example.entity.LeafAlloc;
import com.boot.example.entity.Segment;
import com.boot.example.entity.SegmentBuffer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * com.boot.example.service.SegmentGenerateService
 *
 * @author lipeng
 * @date 2021/2/8 11:11 AM
 */
@Service
@Slf4j
public class SegmentService implements IdGenerate, InitializingBean {

    /**
     * IDCache未初始化成功时的异常码
     */
    private static final long EXCEPTION_ID_CACHE_INIT_FALSE = -1;

    /**
     * key不存在时的异常码
     */
    private static final long EXCEPTION_ID_KEY_NOT_EXISTS = -2;

    /**
     * SegmentBuffer中的两个Segment均未从DB中装载时的异常码
     */
    private static final long EXCEPTION_ID_TWO_SEGMENTS_ARE_NULL = -3;

    /**
     * 最大步长不超过100,0000
     */
    private static final int MAX_STEP = 1000000;

    /**
     * 一个Segment维持时间为15分钟
     */
    private static final long SEGMENT_DURATION = 15 * 60 * 1000L;

    private ExecutorService service = new ThreadPoolExecutor(
            5, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new UpdateThreadFactory());

    private volatile boolean initOK = false;

    private final Map<String, SegmentBuffer> cache = new ConcurrentHashMap<>();

    private final Object mutex = new Object();

    public static class UpdateThreadFactory implements ThreadFactory {

        private static int threadInitNumber = 0;

        private static synchronized int nextThreadNum() {
            return threadInitNumber++;
        }

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "Thread-Segment-Update-" + nextThreadNum());
        }
    }

    @Autowired
    private LeafAllocService leafAllocService;

    @Override
    public boolean init() {
        log.info("Init ...");
        // 确保加载到kv后才初始化成功
        updateCacheFromDb();
        initOK = true;
        updateCacheFromDbAtEveryMinute();
        return initOK;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    private void updateCacheFromDbAtEveryMinute() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r);
            t.setName("check-idCache-thread");
            t.setDaemon(true);
            return t;
        });

        service.scheduleWithFixedDelay(this::updateCacheFromDb, 60, 60, TimeUnit.SECONDS);
    }

    private void updateCacheFromDb() {
        log.info("update cache from db");
        try {
            List<String> dbTags = leafAllocService.listAllTag();
            List<String> cacheTags = new ArrayList<>(cache.keySet());
            Set<String> insertTagsSet = new HashSet<>(dbTags);
            Set<String> removeTagsSet = new HashSet<>(cacheTags);
            // db中新加的tags灌进cache
            for (int i = 0; i < cacheTags.size(); i++) {
                String tmp = cacheTags.get(i);
                if (insertTagsSet.contains(tmp)) {
                    insertTagsSet.remove(tmp);
                }
            }

            for (String tag : insertTagsSet) {
                SegmentBuffer buffer = new SegmentBuffer();
                buffer.setKey(tag);
                Segment segment = buffer.getCurrent();
                segment.setValue(new AtomicLong(0));
                segment.setMax(0L);
                segment.setStep(0);
                cache.put(tag, buffer);
                log.info("Add tag {} from db to IdCache, SegmentBuffer {}", tag, buffer);
            }

            // cache中已失效的tags从cache删除
            for (int i = 0; i < dbTags.size(); i++) {
                String tmp = dbTags.get(i);
                if (removeTagsSet.contains(tmp)) {
                    removeTagsSet.remove(tmp);
                }
            }

            for (String tag : removeTagsSet) {
                cache.remove(tag);
                log.info("Remove tag {} from IdCache", tag);
            }
        } catch (Exception e) {
            log.warn("update cache from db exception", e);
        }
    }

    @Override
    public Result get(final String key) {
        if (!initOK) {
            return new Result(EXCEPTION_ID_CACHE_INIT_FALSE, Status.EXCEPTION);
        }

        if (cache.containsKey(key)) {
            SegmentBuffer buffer = cache.get(key);
            if (!buffer.isInitOk()) {
                synchronized (mutex) {
                    if (!buffer.isInitOk()) {
                        try {
                            updateSegmentFromDb(key, buffer.getCurrent());
                            log.info("Init buffer. Update leaf key {} {} from db", key, buffer.getCurrent());
                            buffer.setInitOk(true);
                        } catch (Exception e) {
                            log.warn("Init buffer {} exception", buffer.getCurrent(), e);
                        }
                    }
                }
            }
            return getIdFromSegmentBuffer(cache.get(key));
        }
        return new Result(EXCEPTION_ID_KEY_NOT_EXISTS, Status.EXCEPTION);
    }

    public void updateSegmentFromDb(String key, Segment segment) {
        SegmentBuffer buffer = segment.getBuffer();
        LeafAlloc leafAlloc;
        if (!buffer.isInitOk()) {
            leafAlloc = leafAllocService.updateMaxIdAndGetLeafAlloc(key);
            buffer.setStep(leafAlloc.getStep());
            // leafAlloc中的step为DB中的step
            buffer.setMinStep(leafAlloc.getStep());
        } else if (buffer.getUpdateTimestamp() == 0) {
            leafAlloc = leafAllocService.updateMaxIdAndGetLeafAlloc(key);
            buffer.setUpdateTimestamp(System.currentTimeMillis());
            buffer.setStep(leafAlloc.getStep());
            buffer.setMinStep(leafAlloc.getStep());//leafAlloc中的step为DB中的step
        } else {
            long duration = System.currentTimeMillis() - buffer.getUpdateTimestamp();
            int nextStep = buffer.getStep();
            if (duration < SEGMENT_DURATION) {
                if (nextStep * 2 > MAX_STEP) {
                    //do nothing
                } else {
                    nextStep = nextStep * 2;
                }
            } else if (duration < SEGMENT_DURATION * 2) {
                //do nothing with nextStep
            } else {
                nextStep = nextStep / 2 >= buffer.getMinStep() ? nextStep / 2 : nextStep;
            }
            log.info("leafKey[{}], step[{}], duration[{}mins], nextStep[{}]", key, buffer.getStep(), String.format("%.2f", ((double) duration / (1000 * 60))), nextStep);
            LeafAlloc temp = new LeafAlloc();
            temp.setBizTag(key);
            temp.setStep(nextStep);
            leafAlloc = leafAllocService.updateMaxIdAndCustomStepByTag(temp);
            buffer.setUpdateTimestamp(System.currentTimeMillis());
            buffer.setStep(nextStep);
            buffer.setMinStep(leafAlloc.getStep());//leafAlloc的step为DB中的step
        }
        // must set value before set max
        long value = leafAlloc.getMaxId() - buffer.getStep();
        segment.getValue().set(value);
        segment.setMax(leafAlloc.getMaxId());
        segment.setStep(buffer.getStep());
    }

    public Result getIdFromSegmentBuffer(final SegmentBuffer buffer) {
        while (true) {
            buffer.rLock().lock();
            try {
                final Segment segment = buffer.getCurrent();
                if (!buffer.isNextReady() && (segment.getIdle() < 0.9 * segment.getStep()) && buffer.getThreadRunning().compareAndSet(false, true)) {
                    service.execute(() -> {
                        Segment next = buffer.getSegments()[buffer.nextPos()];
                        boolean updateOk = false;
                        try {
                            updateSegmentFromDb(buffer.getKey(), next);
                            updateOk = true;
                            log.info("update segment {} from db {}", buffer.getKey(), next);
                        } catch (Exception e) {
                            log.warn(buffer.getKey() + " updateSegmentFromDb exception", e);
                        } finally {
                            if (updateOk) {
                                buffer.wLock().lock();
                                buffer.setNextReady(true);
                                buffer.getThreadRunning().set(false);
                                buffer.wLock().unlock();
                            } else {
                                buffer.getThreadRunning().set(false);
                            }
                        }
                    });
                }
                long value = segment.getValue().getAndIncrement();
                if (value < segment.getMax()) {
                    return new Result(value, Status.SUCCESS);
                }
            } finally {
                buffer.rLock().unlock();
            }
            waitAndSleep(buffer);

            buffer.wLock().lock();
            try {
                final Segment segment = buffer.getCurrent();
                long value = segment.getValue().getAndIncrement();
                if (value < segment.getMax()) {
                    return new Result(value, Status.SUCCESS);
                }
                if (buffer.isNextReady()) {
                    buffer.switchPos();
                    buffer.setNextReady(false);
                } else {
                    log.error("Both two segments in {} are not ready!", buffer);
                    return new Result(EXCEPTION_ID_TWO_SEGMENTS_ARE_NULL, Status.EXCEPTION);
                }
            } finally {
                buffer.wLock().unlock();
            }
        }
    }

    private void waitAndSleep(SegmentBuffer buffer) {
        int roll = 0;
        while (buffer.getThreadRunning().get()) {
            roll += 1;
            if (roll > 10000) {
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                    break;
                } catch (InterruptedException e) {
                    log.warn("Thread {} Interrupted", Thread.currentThread().getName());
                    break;
                }
            }
        }
    }
}
