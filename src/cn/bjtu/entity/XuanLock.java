package cn.bjtu.entity;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author chancey
 * @create 2020-09-22 10:27
 */
public class XuanLock {
    public static void main(String[] args) {
        XuanLock xuanLock = new XuanLock();
        new Thread(() -> {
            xuanLock.lock();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                xuanLock.unlock();
            }
        }, "A").start();

        new Thread(() -> {
            xuanLock.lock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                xuanLock.unlock();
            }
        }, "B").start();
    }

    AtomicReference atomicReference = new AtomicReference();

    public void lock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t" + "尝试获取锁");
        while (!atomicReference.compareAndSet(null, thread)){

        }
    }

    public void unlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + "\t" + "解锁成功");
    }
}
