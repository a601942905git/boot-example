package com.boot.example;

import com.boot.example.snow.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * com.boot.example.BusienssCodeWorkerTest
 *
 Long.toString(421054244322415195
 Long.toString(421054244322415196
 Long.toString(421054244322415197
 Long.toString(421054244322415198
 Long.toString(421054244322415199
 Long.toString(421054244322415200
 Long.toString(421054244322415201
 Long.toString(421054244322415202
 Long.toString(421054244322415203
 Long.toString(421054244322415204
 Long.toString(421054244322415205
 Long.toString(421054244322415206
 Long.toString(421054244322415207
 * 雪花算法可以保证唯一性，却存在以下缺点：
 * 1.根据一个id可以猜出其它id
 * 2.根据id可以猜出表中记录数
 * 为了避免这些带来的问题，可以进行进行转换
 * 通过Long.toString()方法进行进制转换后的结果如下：
 * next id：8au6j7evn9bj
 * next id：8au6j7evn9bk
 * next id：8au6j7evn9bl
 * next id：8au6j7evn9bm
 * next id：8au6j7evn9bn
 * next id：8au6j7evn9bo
 * next id：8au6j7evn9bp
 * next id：8au6j7evn9bq
 * next id：8au6j7evn9br
 * next id：8au6j7evn9bs
 * next id：8au6j7evn9bt
 * next id：8au6j7evn9bu
 * next id：8au6j7evn9bv
 *
 * 重写进制转换方法，转换结果如下：
 * next id：plurz7e4xfbz
 * next id：plurz7e4xfb1
 * next id：plurz7e4xfba
 * next id：plurz7e4xfbm
 * next id：plurz7e4xfbx
 * next id：plurz7e4xfbo
 * next id：plurz7e4xfb8
 * next id：plurz7e4xfbq
 * next id：plurz7e4xfb6
 * next id：plurz7e4xfb0
 * next id：plurz7e4xfbt
 * next id：plurz7e4xfbu
 * next id：plurz7e4xfb4
 *
 * @author lipeng
 * @date 2021/12/4 2:18 PM
 */
@Slf4j
public class BusinessCodeWorkerTest {

    @Test
    public void test() {
        LocalDateTime localDateTime1 = LocalDateTime.of(2018, 1, 1, 0, 0, 0);
        LocalDateTime localDateTime2 = LocalDateTime.now();
        long daysOffset = ChronoUnit.DAYS.between(localDateTime1, localDateTime2);
        long hoursOffset = DateUtils.getFragmentInHours(Calendar.getInstance(), Calendar.DAY_OF_MONTH);

        String daysOffsetStr = Long.toString(daysOffset, 33);
        String hoursOffsetStr = Long.toString(hoursOffset, 33);
        long sequence = 2428734987L;
        Set<String> businessCodeSet = new HashSet<>();
        int loopCount = 10000;
        for (int i = 0; i < loopCount; i++) {
            sequence = sequence + i;
            String businessCode = daysOffsetStr + hoursOffsetStr + Long.toString(sequence, 33);
            businessCodeSet.add(businessCode);
            log.info("business code：" + businessCode);
        }

        log.info("business code set size：" + businessCodeSet.size());
    }

    @Test
    public void test1() {
        IdWorker idWorker = new IdWorker(5, 5);
        for (int i = 0; i < 100; i++) {
            log.info("business code：" + Long.toString(idWorker.nextId(), 33));
        }
    }

    @Test
    public void test005() {
        IdWorker idWorker = new IdWorker(5, 5);
        for (int i = 0; i < 100; i++) {
            log.info("business code：" + Long.toString(idWorker.nextId(), 33));
        }
    }

    public static void main(String[] args) {
        System.out.println("next id：" + Long.toString(421054244322415195L, 33));
        System.out.println("next id：" + Long.toString(421054244322415196L, 33));
        System.out.println("next id：" + Long.toString(421054244322415197L, 33));
        System.out.println("next id：" + Long.toString(421054244322415198L, 33));
        System.out.println("next id：" + Long.toString(421054244322415199L, 33));
        System.out.println("next id：" + Long.toString(421054244322415200L, 33));
        System.out.println("next id：" + Long.toString(421054244322415201L, 33));
        System.out.println("next id：" + Long.toString(421054244322415202L, 33));
        System.out.println("next id：" + Long.toString(421054244322415203L, 33));
        System.out.println("next id：" + Long.toString(421054244322415204L, 33));
        System.out.println("next id：" + Long.toString(421054244322415205L, 33));
        System.out.println("next id：" + Long.toString(421054244322415206L, 33));
        System.out.println("next id：" + Long.toString(421054244322415207L, 33));
    }
}
