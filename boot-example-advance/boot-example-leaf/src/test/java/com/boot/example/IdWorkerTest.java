package com.boot.example;

import com.boot.example.snow.IdWorker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * com.boot.example.IdWorkerTest
 *
 * @author lipeng
 * @date 2021/6/29 10:25 AM
 */
public class IdWorkerTest {

    @Test
    public void nextId() throws InterruptedException {
        IdWorker idWorker = new IdWorker(1, 1);
        while (true) {
            Set<Long> idSet = new HashSet<>();
            int loopCount = 30;
            for (int i = 0; i < loopCount; i++) {
                long nextId = idWorker.nextId();
                System.out.println("next id：" + nextId);
                idSet.add(nextId);
            }

            Assertions.assertEquals(idSet.size(), loopCount);
            TimeUnit.SECONDS.sleep(1);
            System.out.println("==========================");
        }
    }
}
