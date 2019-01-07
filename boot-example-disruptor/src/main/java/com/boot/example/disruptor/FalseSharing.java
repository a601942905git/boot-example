package com.boot.example.disruptor;

/**
 * com.boot.example.disruptor.FalseSharing
 *
 * @author lipeng
 * @date 2018/12/28 下午8:13
 */
public class FalseSharing implements Runnable{

    public final static int NUM_THREADS = 4;
    public final static long ITERATIONS = 500L * 1000L * 1000L;
    private final int arrayIndex;

    private static VolatileLong[] longs = new VolatileLong[NUM_THREADS];

    static {
        for (int i = 0; i < longs.length; i++) {
            longs[i] = new VolatileLong();
        }
    }

    public FalseSharing(final int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    public static void main(final String[] args) throws Exception {
        final long start = System.currentTimeMillis();
        runTest();
        System.out.println("duration = " + (System.currentTimeMillis() - start));
    }

    private static void runTest() throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseSharing(i));
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
    }

    @Override
    public void run() {
        long i = ITERATIONS + 1;
        while (0 != --i) {
            longs[arrayIndex].value = i;
        }
        System.out.println("当前线程：" + Thread.currentThread().getName() +
                "，执行结果：" + longs[arrayIndex].value);
    }

    public final static class VolatileLong {
        public volatile long value = 0L;
        /**
         * 填充，可以注释后对比测试
         */
        public long p1, p2, p3, p4, p5, p6;
    }
}
