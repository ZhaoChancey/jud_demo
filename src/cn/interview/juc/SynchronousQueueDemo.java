package cn.interview.juc;

import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author chancey
 * @create 2020-08-02 13:17
 */
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        BlockingQueue blockingQueue = new SynchronousQueue<String>();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "\t 1");
                blockingQueue.put("1");

                System.out.println(Thread.currentThread().getName() + "\t 2");
                blockingQueue.put("2");

                System.out.println(Thread.currentThread().getName() + "\t 3");
                blockingQueue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AAA").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(blockingQueue.take());

                TimeUnit.SECONDS.sleep(3);
                System.out.println(blockingQueue.take());

                TimeUnit.SECONDS.sleep(3);
                System.out.println(blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BBB").start();
    }
}
