package cn.interview.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author chancey
 * @create 2020-08-02 12:22
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);//模拟三个停车位，即共享资源数量
        int flag = 1;
        for (int i = 1; i <= 6; i++) {//模拟6个车，即6个线程
            new Thread(() -> {
                try {
                    semaphore.acquire();//表示有三个线程争抢到了资源
                    System.out.println(Thread.currentThread().getName() + "\t 抢到了车位");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName() + "\t 离开了车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            },String.valueOf(i)).start();
        }
    }
}
