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
     * 得到0000000000000000000000000000000000000000000000000000111111111111
     */
    private final long sequenceMask = ~(-1L << sequenceBits);

    private long lastTimestamp = -1L;


    public IdWorker(long workerId, long datacenterId){
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        //时间回拨，抛出异常
        if (timestamp < lastTimestamp) {
            System.err.printf("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp);
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
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
     * 当前ms已经满了
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
