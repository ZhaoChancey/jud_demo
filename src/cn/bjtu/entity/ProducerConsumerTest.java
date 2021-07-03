package cn.bjtu.entity;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chancey
 * @create 2020-09-21 18:13
 */
public class ProducerConsumerTest {
    public static void main(String[] args) {
        ProducerConsumer producerConsumer = new ProducerConsumer();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(200);
                    producerConsumer.put();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(300);
                    producerConsumer.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

    }
}

class ProducerConsumer{
    int num = 0;
//    Lock lock = new ReentrantLock();
//    Condition condition = lock.newCondition();
//
//    public void put() throws InterruptedException {
//        lock.lock();
//        while (num != 0) {
//            condition.await();
//        }
//        num++;
//        System.out.println(Thread.currentThread().getName() + ":" + num);
//        condition.signal();
//        lock.unlock();
//    }
//
//    public void take() throws InterruptedException {
//        lock.lock();
//        while (num == 0) {
//            condition.await();
//        }
//        num--;
//        System.out.println(Thread.currentThread().getName() + ":" + num);
//        condition.signal();
//        lock.unlock();
//    }
    public synchronized void put() throws InterruptedException {
        while (num != 0) {
            this.wait();
        }
        num++;
        System.out.println(Thread.currentThread().getName() + ":" + num);
        this.notify();
    }

    public synchronized void take() throws InterruptedException {
        while (num == 0) {
            this.wait();
        }
        num--;
        System.out.println(Thread.currentThread().getName() + ":" + num);
        this.notify();
    }
}
