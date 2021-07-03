package cn.interview.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** 可重入锁（递归锁）
 * @author chancey
 * @create 2020-08-01 18:36
 */
public class ReenterLockDemo {
    public static void main(String[] args) throws InterruptedException {
        Phone phone = new Phone();

        new Thread(() -> {
            phone.sendMs();
        },"t1").start();

        new Thread(() -> {
            phone.sendMs();
        },"t2").start();

        TimeUnit.SECONDS.sleep(1);

        System.out.println();
        System.out.println();

        Thread t3 = new Thread(phone,"t3");
        Thread t4 = new Thread(phone,"t4");
        t3.start();
        t4.start();
    }
}
class Phone implements Runnable{
    //Synchronized TEST
    public synchronized void sendMs(){
        System.out.println(Thread.currentThread().getName() + "\t invoke sendMs");
        sendEmail();
    }

    public synchronized void sendEmail(){
        System.out.println(Thread.currentThread().getName()+"\t"+"sendEmail()");
    }

    //Reentrant TEST
    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }

    public void get() {
        lock.lock();
        //注意，加锁几次就要解锁几次，不能不匹配
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t"+"get()");
            set();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            lock.unlock();
        }
    }

    public void set(){
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t"+"set()");
        }finally {
            lock.unlock();
        }
    }
}
