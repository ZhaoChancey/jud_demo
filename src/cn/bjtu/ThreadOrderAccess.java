package cn.bjtu;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chancey
 * @create 2020-07-15 20:20
 * 多线程之间按顺序调用，实现A->B->C
 * 三个线程启动，要求：
 * AA打印5次，BB打印10次，CC打印15次
 * 接着
 * AA打印5次，BB打印10次，CC打印15次
 * ...来10轮
 *
 *  1.高低聚合前提下，线程操作资源类
 *  2.判断/干活/通知
 *  3.多线程交互（有wait和notify）中，必须要防止多线程的虚假唤醒，即（判断只能用while，不能用if）
 *  4.注意标志位的修改和定位[精准通知，精准唤醒]
 */
class ShareResource {
    private int number = 1;//1:A 2:B 3:C
    private Lock lock = new ReentrantLock();
    //一把锁（lock）三把钥匙
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void print5(){
        lock.lock();
        try {
            //1.判断
            while (number != 1) {
                condition1.await();//如果num不为1，A线程就要等着
            }
            //2.干活
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //3.通知
            number = 2;
            condition2.signal();//唤醒B
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10(){
        lock.lock();
        try {
            //1.判断
            while (number != 2) {
                condition2.await();//如果num不为2，B线程就要等着
            }
            //2.干活
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //3.通知
            number = 3;
            condition3.signal();//唤醒C
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15(){
        lock.lock();
        try {
            //1.判断
            while (number != 3) {
                condition3.await();//如果num不为3，c线程就要等着
            }
            //2.干活
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //3.通知
            number = 1;
            condition1.signal();//唤醒A
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
public class ThreadOrderAccess {
    public static void main(String[] args) {
        ShareResource resource = new ShareResource();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                resource.print5();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                resource.print10();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                resource.print15();
            }
        }, "C").start();
    }
}
