package cn.bjtu;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/** 集齐七颗龙珠才能召唤神龙、人齐了才能开会
 * CyclicBarrier做加法
 *  CyclicBarrier
 *  * 的字面意思是可循环（Cyclic）使用的屏障（Barrier）。它要做的事情是，
 *  * 让一组线程到达一个屏障（也可以叫同步点）时被阻塞，
 *  * 直到最后一个线程到达屏障时，屏障才会开门，所有
 *  * 被屏障拦截的线程才会继续干活。
 *  * 线程进入屏障通过CyclicBarrier的await()方法。
 * @author chancey
 * @create 2020-07-16 12:58
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        //CyclicBarrier(int parties, Runnable barrierAction)
        CyclicBarrier barrier = new CyclicBarrier(7, () -> {
            System.out.println("集齐7颗龙珠就可以召唤神龙");
        });

        for (int i = 1; i <= 7; i++) {
            final int temp = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t收集到第" + temp + "颗龙珠" );
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
