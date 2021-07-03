package cn.interview.juc;

import java.util.concurrent.CountDownLatch;

/**
 * @author chancey
 * @create 2020-08-02 10:56
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        /*CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"国被灭");
                countDownLatch.countDown();
            }, CountryEnum.for_each(i).getRetMessage()).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t ******秦国一统华夏");*/
        CountDownLatchDemo countDownLatchDemo = new CountDownLatchDemo();
        countDownLatchDemo.closeDoor();
    }

    public void closeDoor() throws InterruptedException {
        //保证只有所有同学离开教室之后班长才能锁门
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"\t上完自习，离开教室");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t ******班长最后关门走人");
    }
}
