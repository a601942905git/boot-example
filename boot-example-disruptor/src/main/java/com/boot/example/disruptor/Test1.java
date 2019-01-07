package com.boot.example.disruptor;

/**
 * com.boot.example.disruptor.Test1
 *
 * @author lipeng
 * @date 2018/12/29 下午3:41
 */
public class Test1 {

    public static void main(String[] args) {
        ThreadDemo threadDemo = new ThreadDemo();
        new Thread(threadDemo).start();
        while (true) {
            if (threadDemo.flag) {
                System.out.println("-------------");
                break;
            }
        }
        System.out.println("=========");
    }

    static class ThreadDemo implements Runnable {

        private volatile boolean flag = false;

        @Override
        public void run() {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.flag = true;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }
    }
}
