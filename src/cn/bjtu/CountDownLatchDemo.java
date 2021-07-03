package cn.bjtu;

import java.util.concurrent.CountDownLatch;

/** 需求：在主线程中，必须等待其他线程执行完才能执行最后一步
 CountDownLatch 做减法
 *      CountDownLatch  主要有两个方法，当一个或多个线程调用await方法时，这些线程会阻塞。
 *  * 其它线程调用countDown方法会将计数器减1(调用countDown方法的线程不会阻塞)，
 *  * 当计数器的值变为0时，因await方法阻塞的线程会被唤醒，继续执行。
 * @author chancey
 * @create 2020-07-16 12:40
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t" + "离开教室");
                countDownLatch.countDown();//在多线程中调用countDown不会阻塞
            }, String.valueOf(i)).start();
        }
        countDownLatch.await();//在main线程调用await，主线程阻塞
        System.out.println(Thread.currentThread().getName() + "\t" + "班长关门走人");
    }

    public void closeDoor(){
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t" + "离开教室");
            }, String.valueOf(i)).start();
        }

        System.out.println(Thread.currentThread().getName() + "\t" + "班长关门走人");
    }
}
