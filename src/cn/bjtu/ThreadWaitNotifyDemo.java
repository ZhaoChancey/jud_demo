package cn.bjtu;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chancey
 * @create 2020-07-15 15:48
 * 题目：现在两个线程，可以操作初始值为0的一个变量
 * 实现一个线程对该变量加1，一个线程对该变量减1，
 * 实现交替，来10轮，变量初始值为0
 * 扩展到4个线程
 *
 *  1.高低聚合前提下，线程操作资源类
 *  2.判断/干活/通知
 *  3.多线程交互（有wait和notify）中，必须要防止多线程的虚假唤醒，即（判断只能用while，不能用if）
 *      /* 把while改成if体会一下
 *      当一个条件满足时，很多线程都被唤醒了，但是只有其中部分是有用的唤醒，其它的唤醒都是无用功
 *      1.比如说买货，如果商品本来没有货物，突然进了一件商品，这是所有的线程都被唤醒了
 * 	，   但是只能一个人买，所以其他人都是假唤醒，获取不到对象的锁*、
 */
class AirConditioner{

    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment(){
        lock.lock();
        try {
            //1.判断
            while (number != 0) {
                condition.await();//this.wait();
            }
            //2.干活
            number++;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            //3.通知
            condition.signalAll();//this.notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement(){
        lock.lock();
        try {
            //1.判断
            while (number == 0) {
                condition.await();//this.wait();
            }
            //2.干活
            number--;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            //3.通知
            condition.signalAll();//this.notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

   /* //生产者
    public synchronized void increment() throws InterruptedException {
        //1.判断
        while (number != 0) {
            this.wait();
        }
        //2.干活
        number++;
        System.out.println(Thread.currentThread().getName() + "\t" + number);
        //3.通知
        this.notifyAll();
    }

    //消费者
    public synchronized void decrement() throws InterruptedException {
        //1.判断
        while (number == 0) {
            this.wait();
        }
        //2.干活
        number--;
        System.out.println(Thread.currentThread().getName() + "\t" + number);
        //3.通知
        this.notifyAll();
    }*/
}
public class ThreadWaitNotifyDemo {
    public static void main(String[] args) {
        AirConditioner airConditioner = new AirConditioner();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    Thread.sleep(200);
                    airConditioner.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    Thread.sleep(300);
                    airConditioner.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    Thread.sleep(400);
                    airConditioner.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    Thread.sleep(500);
                    airConditioner.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();
//        for (int i = 0; i < 26; i++) {
//            System.out.println((char)('A' + i));
//        }
    }
}
