package cn.bjtu.entity;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chancey
 * @create 2020-09-21 20:26
 */
public class PCQueue {
    public static void main(String[] args) {
        PC pc = new PC();
        new Thread(() -> {
            try {
                pc.put();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"producer").start();

        new Thread(() -> {
            try {
                pc.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"consumer").start();
    }
}

class PC{
    BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
    int i = 0;

    public void put() throws InterruptedException {
        while (true) {
            queue.put(i);
            System.out.println(Thread.currentThread().getName() + "生产了" + i % 5);
            i++;
            Thread.sleep(500);
        }
    }

    public void get() throws InterruptedException {
        while (true) {
            int take = queue.take();
            System.out.println(Thread.currentThread().getName() + "消费了" + take % 5);
            Thread.sleep(800);
        }
    }
}
