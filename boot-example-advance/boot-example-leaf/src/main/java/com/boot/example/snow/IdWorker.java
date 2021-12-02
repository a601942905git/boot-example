package com.boot.example.snow;

/**
 * com.boot.example.snow.IdWorker
 *
 * 雪花算法64位整型实现
 * 标识位：1
 * 时间戳：41位
 * 机器码：10位(机器id + 数据中心id)
 * 序列号：12位(毫秒内生成的序列号)
 *
 * |运算：一个为1结果为1
 * &运算：都为1结果为1
 *
 * 用42位存储时间戳，(1L << 42) / (1000L * 60 * 60 * 24 * 365) = 139年
 * 用41位存储时间戳，(1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69年
 * 用40位存储时间戳，(1L << 40) / (1000L * 60 * 60 * 24 * 365) = 34年
 * 用39位存储时间戳，(1L << 39) / (1000L * 60 * 60 * 24 * 365) = 17年
 * 用38位存储时间戳，(1L << 38) / (1000L * 60 * 60 * 24 * 365) = 8年
 * 用37位存储时间戳，(1L << 37) / (1000L * 60 * 60 * 24 * 365) = 4年
 *
 * @author lipeng
 * @date 2021/6/28 5:49 PM
 */
public class IdWorker {

    /**
     * 机器id
     */
    private final long workerId;

    /**
     * 数据中心id
     */
    private final long datacenterId;

    /**
     * 序号
     */
    private long sequence = 0;

    /**
     * 2018/9/29日，从此时开始计算，可以用到2089年
     */
    private final long twepoch = 1538211907857L;

    /**
     * 机器id位数
     */
    private final long workerIdBits = 5L;

    /**
     * 数据中心id位数
     */
    private final long datacenterIdBits = 5L;

    /**
     * 序号位数
     */
    private final long sequenceBits = 12L;

    /**
     * 机器id偏移位数
     */
    private final long workerIdShift = sequenceBits;

    /**
     * 数据中心id偏移位数
     */
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    /**
     * 时间戳偏移位数
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /**
     * 结果为4095
     * 0000000000000000000000000000000000000000000000000000111111111111(4095)
     * 0000000000000000000000000000000000000000000000000000111110100000(4000)
     * 0000000000000000000000000000000000000000000000000001000000000000(4096)
     */
    private final long sequenceMask = ~(-1L << sequenceBits);

    private long lastTimestamp = -1L;


    public IdWorker(long workerId, long datacenterId){
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * 雪花算法生成唯一id逻辑：
     * 1.当前生成的时间与上次生成的时间比较
     *  1.1 如果相等，则说明是同一毫秒生成唯一id，判断序列号
     *      1.1.1 序列号大于最大序列号4096，则需要在下一毫秒生成唯一id
     *      1.1.2 序列号小于最大序列号4096，直接将序列号 + 1
     *  1.2 如果不相等，则说明不是同一毫秒生成唯一id，直接将序列号设置为0
     *
     * @return 唯一id
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        // 时间回拨，抛出异常
        if (timestamp < lastTimestamp) {
            System.err.printf("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp);
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }

        // 同一毫秒内生成唯一id，需要对序列号 + 1
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            // 同一毫秒内序列号超过4096，则需要在下一毫秒生成唯一id
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }

        // 非同一毫秒内生成唯一id，直接将序列号设置为0
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;
        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 最后生成时间戳
     * @return 新时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 生成时间戳
     *
     * @return 时间戳
     */
    private long timeGen(){
        return System.currentTimeMillis();
    }
}
