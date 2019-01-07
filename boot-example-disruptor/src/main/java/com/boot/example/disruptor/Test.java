package com.boot.example.disruptor;

/**
 * com.boot.example.disruptor.Test3
 * 此示例指明RingBufferSize大小为2的次方的原因
 * 一个数 % 2的次方和一个数 & (2的次方 - 1)结果是一样的
 * 但是位运算的速度高于%运算
 * @author lipeng
 * @date 2018/12/27 下午9:44
 */
public class Test {

    /**
     *
     */
    static  long[][] arr;

    public static void main(String[] args) {
        arr = new long[1024 * 1024][];
        for (int i = 0; i < 1024 * 1024; i++) {
            arr[i] = new long[8];
            for (int j = 0; j < 8; j++) {
                arr[i][j] = 0L;
            }
        }
        long sum = 0L;
        long marked = System.currentTimeMillis();
        for (int i = 0; i < 1024 * 1024; i+=1) {
            for(int j =0; j< 8;j++){
                sum = arr[i][j];
            }
        }
        System.out.println("Loop times:" + (System.currentTimeMillis() - marked) + "ms");

        marked = System.currentTimeMillis();
        for (int i = 0; i < 8; i+=1) {
            for(int j =0; j< 1024 * 1024;j++){
                sum = arr[j][i];
            }
        }
        System.out.println("Loop times:" + (System.currentTimeMillis() - marked) + "ms");
    }
}
