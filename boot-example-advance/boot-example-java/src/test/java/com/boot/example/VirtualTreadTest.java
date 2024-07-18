package com.boot.example;

import org.junit.jupiter.api.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @see java.lang.VirtualThread#createDefaultScheduler()
 *
 * @author lipeng
 * &#064;date 2024/6/5 17:04:04
 */
public class VirtualTreadTest {

    static List<Integer> list = new ArrayList<>();

    /**
     * 打印线程信息
     *
     * @throws InterruptedException
     */
    @Test
    public void printThreadInfo() throws InterruptedException {
        // Thread[#24,Thread-0,5,main]
        Thread.ofPlatform().start(() -> System.out.println(Thread.currentThread()));

        // VirtualThread[#25]/runnable@ForkJoinPool-1-worker-1
        Thread virtualThread1 = Thread.ofVirtual().start(() -> System.out.println(Thread.currentThread()));
        Thread virtualThread2 = Thread.startVirtualThread(() -> System.out.println(Thread.currentThread()));
        Thread virtualThread3 = Thread.startVirtualThread(() -> System.out.println(Thread.currentThread()));
        Thread virtualThread4 = Thread.startVirtualThread(() -> System.out.println(Thread.currentThread()));
        Thread virtualThread5 = Thread.startVirtualThread(() -> System.out.println(Thread.currentThread()));
        virtualThread1.join();
        virtualThread2.join();
        virtualThread3.join();
        virtualThread4.join();
        virtualThread5.join();
        getThreadInfo();
    }

    @Test
    public void test() throws InterruptedException {
        // 开启线程 统计平台线程数
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
            ThreadInfo[] threadInfo = threadBean.dumpAllThreads(false, false);
            updateMaxThreadNum(threadInfo.length);
        }, 1, 1, TimeUnit.MILLISECONDS);


        long start = System.currentTimeMillis();
        try (var executor = getExecutorService()) {
            IntStream.range(0, 10_000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });
        }  // executor.close() is called implicitly, and waits

        scheduledExecutorService.close();
        System.out.println("max：" + list.get(0) + " platform thread/os thread");
        System.out.printf("totalMillis：%dms\n", System.currentTimeMillis() - start);
    }

    private ExecutorService getExecutorService() {
        // max：19 platform thread/os thread totalMillis：193ms
//        return Executors.newVirtualThreadPerTaskExecutor();
        // max：210 platform thread/os thread totalMillis：1378ms
        return Executors.newFixedThreadPool(200);
    }

    public void sleep() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 更新创建的平台最大线程数
    private static void updateMaxThreadNum(int num) {
        if (list.isEmpty()) {
            list.add(num);
        } else {
            Integer integer = list.get(0);
            if (num > integer) {
                list.add(0, num);
            }
        }
    }

    public static void getThreadInfo() {
        // 获取ThreadMXBean实例
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        // 获取当前应用的线程总数
        int threadCount = threadMXBean.getThreadCount();
        System.out.println("Thread Count: " + threadCount);

        // 获取峰值线程数
        int peakThreadCount = threadMXBean.getPeakThreadCount();
        System.out.println("Peak Thread Count: " + peakThreadCount);

        // 获取守护线程数
        int daemonThreadCount = threadMXBean.getDaemonThreadCount();
        System.out.println("Daemon Thread Count: " + daemonThreadCount);

        // 获取总的线程已启动并且尚未终止的线程数
        long totalStartedThreadCount = threadMXBean.getTotalStartedThreadCount();
        System.out.println("Total Started Thread Count: " + totalStartedThreadCount);

        // 获取所有线程的ID及相关信息
        long[] threadIds = threadMXBean.getAllThreadIds();
        for (long threadId : threadIds) {
            System.out.println("Thread ID: " + threadId + ", Thread Info: " +
                    threadMXBean.getThreadInfo(threadId));
        }
    }
}
