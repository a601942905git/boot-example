package com.boot.example;

import org.junit.jupiter.api.Test;

/**
 * -XX:NativeMemoryTracking=detail
 * @author lipeng
 * &#064;date 2024/6/12 11:15:14
 */
public class MemoryTest {

    public static final Integer COUNT = 4000;

    /**
     * Total: reserved=14023421KB, committed=8663965KB
     *        malloc: 52497KB #182017
     *        mmap:   reserved=13970924KB, committed=8611468KB
     *
     * -                 Java Heap (reserved=4194304KB, committed=264192KB)
     *                             (mmap: reserved=4194304KB, committed=264192KB)
     *
     * -                     Class (reserved=1049863KB, committed=1927KB)
     *                             (classes #2152)
     *                             (  instance classes #1956, array classes #196)
     *                             (malloc=1287KB #13673) (at peak)
     *                             (mmap: reserved=1048576KB, committed=640KB)
     *                             (  Metadata:   )
     *                             (    reserved=65536KB, committed=4032KB)
     *                             (    used=3922KB)
     *                             (    waste=110KB =2.73%)
     *                             (  Class space:)
     *                             (    reserved=1048576KB, committed=640KB)
     *                             (    used=598KB)
     *                             (    waste=42KB =6.50%)
     *
     * -                    Thread (reserved=8296134KB, committed=8296134KB)
     *                             (thread #4021)
     *                             (stack: reserved=8283260KB, committed=8283260KB)
     *                             (malloc=8163KB #24386) (peak=8202KB #24390)
     *                             (arena=4711KB #8041) (at peak)
     * @throws InterruptedException
     */
    @Test
    public void test1() throws InterruptedException {
        for (int i = 0; i < COUNT; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(Long.MAX_VALUE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
        Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * Total: reserved=5792208KB, committed=433328KB
     *        malloc: 28324KB #80942
     *        mmap:   reserved=5763884KB, committed=405004KB
     * -                 Java Heap (reserved=4194304KB, committed=264192KB)
     *                             (mmap: reserved=4194304KB, committed=264192KB)
     * -                     Class (reserved=1048869KB, committed=997KB)
     *                             (classes #2251)
     *                             (  instance classes #2042, array classes #209)
     *                             (malloc=293KB #6230) (at peak)
     *                             (mmap: reserved=1048576KB, committed=704KB)
     *                             (  Metadata:   )
     *                             (    reserved=65536KB, committed=4544KB)
     *                             (    used=4462KB)
     *                             (    waste=82KB =1.81%)
     *                             (  Class space:)
     *                             (    reserved=1048576KB, committed=704KB)
     *                             (    used=644KB)
     *                             (    waste=60KB =8.54%)
     * -                    Thread (reserved=76327KB, committed=76327KB)
     *                             (thread #37)
     *                             (stack: reserved=76220KB, committed=76220KB)
     *                             (malloc=64KB #229) (peak=73KB #233)
     *                             (arena=42KB #73) (peak=169KB #71)
     *
     * @throws InterruptedException
     */
    @Test
    public void test2() throws InterruptedException {
        for (int i = 0; i < COUNT; i++) {
            Thread.startVirtualThread(() -> {
                try {
                    Thread.sleep(Long.MAX_VALUE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        Thread.sleep(Long.MAX_VALUE);
    }
}
