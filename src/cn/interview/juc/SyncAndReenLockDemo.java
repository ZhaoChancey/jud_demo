package cn.interview.juc;

import sun.plugin.WJcovUtil;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** 多线程之间按顺序调用，实现A->B->C
 * 三个线程启动，要求：
 * AA打印5次，BB打印10次，CC打印15次
 * 接着
 * AA打印5次，BB打印10次，CC打印15次
 * ...来10轮
 * @author chancey
 * @create 2020-08-02 15:54
 */
public class SyncAndReenLockDemo {
    public static void main(String[] args) {
        ShareRes shareRes = new ShareRes();

        /*new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareRes.print5();
            }
        },"A").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareRes.print10();
            }
        },"B").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareRes.print15();
            }
        },"C").start();*/

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareRes.print(1, 2, 5,  shareRes.getCondition1(), shareRes.getCondition2());
            }
        },"A").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareRes.print(2, 3, 10, shareRes.getCondition2(), shareRes.getCondition3());
            }
        },"B").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareRes.print(3, 1, 15, shareRes.getCondition3(), shareRes.getCondition1());
            }
        },"C").start();
    }
}
class ShareRes{
    private int number = 1;//1 A ; 2 B ; 3 C
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();//一把锁三把钥匙
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public Condition getCondition1() {
        return condition1;
    }

    public Condition getCondition2() {
        return condition2;
    }

    public Condition getCondition3() {
        return condition3;
    }

    //    public void print5(){
//        lock.lock();
//        try {
//            //1.判断
//            while (number != 1) {
//                condition1.await();//让condition1等着
//            }
//            //2.干活
//            for (int i = 1; i <= 5; i++) {
//                System.out.println(Thread.currentThread().getName() + "\t" + i);
//            }
//            //3.通知
//            number = 2;
//            condition2.signal();//唤醒condition2
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            lock.unlock();
//        }
//    }
//
//    public void print10(){
//        lock.lock();
//        try {
//            //1.判断
//            while (number != 2) {
//                condition2.await();//让condition1等着
//            }
//            //2.干活
//            for (int i = 1; i <= 10; i++) {
//                System.out.println(Thread.currentThread().getName() + "\t" + i);
//            }
//            //3.通知
//            number = 3;
//            condition3.signal();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            lock.unlock();
//        }
//    }
//
//    public void print15(){
//        lock.lock();
//        try {
//            //1.判断
//            while (number != 3) {
//                condition3.await();//让condition1等着
//            }
//            //2.干活
//            for (int i = 1; i <= 15; i++) {
//                System.out.println(Thread.currentThread().getName() + "\t" + i);
//            }
//            //3.通知
//            number = 1;
//            condition1.signal();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            lock.unlock();
//        }
//    }

    public void print(int curNum, int newNum, int count, Condition curCondition, Condition newCondition) {
        lock.lock();
        try {
            while (number != curNum) {
                curCondition.await();
            }
            for (int i = 1; i <= count; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            number = newNum;
            newCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
