package com.guyue.common.sequence;

/**
 * @ClassName SnowFlake
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/9 上午10:18
 **/
public class SnowFlake {
    private static long sequenceLen = 12;
    private static long sequence = 0;

    private static long lastTimestamp = -1L;
    // 每次都会产生一个唯一的序列号
    public static synchronized long nextId() {
        long timestamp = System.currentTimeMillis();
        if (lastTimestamp == timestamp) {
            long sequenceMask = -1L ^ (-1L << sequenceLen);
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                System.out.println("-----------------------------------------");
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        return ((timestamp - 1585644268888L) << sequenceLen) |
                sequence;
    }

    /**
     * 当某一毫秒的时间，产生的id数 超过4095，系统会进入等待，直到下一毫秒，系统继续产生ID
     *
     * @param lastTimestamp
     * @return
     */
    private static long tilNextMillis(long lastTimestamp) {

        long timestamp = System.currentTimeMillis();

        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

}
