package cn.interview.juc;

import java.util.concurrent.TimeUnit;

/**
 * @author chancey
 * @create 2020-08-03 13:43
 */
class HoldLockThread implements Runnable{
    private String lockA;
    private String lockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t自己持有：" + lockA + "\t尝试获取：" + lockB);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t自己持有：" + lockB + "\t尝试获取：" + lockA);
            }
        }
    }
}

public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new HoldLockThread(lockA, lockB), "AAA").start();
        new Thread(new HoldLockThread(lockB, lockA), "BBB").start();

        /**
         * linux  ps -ef | ***
         *
         * windows下java运行程序  也有类似ps的查看进程的命令，只需要查看java
         *      jps = java ps       jps -l :查看运行中的java程序，定位进程号
         *      jstack 进程号：找到死锁位置
         *
         */
    }
}
