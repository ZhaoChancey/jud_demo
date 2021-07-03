package cn.bjtu.entity;

/**
 * * 多线程之间按顺序调用，实现A->B->C
 *  * 三个线程启动，要求：
 *  * AA打印5次，BB打印10次，CC打印15次
 *  * 接着
 *  * AA打印5次，BB打印10次，CC打印15次
 *  * ...来10轮
 * @author chancey
 * @create 2020-09-21 20:12
 */
public class ABCPrint {
    public static void main(String[] args) {
        MyThread2 myThread2 = new MyThread2();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    myThread2.print5();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    myThread2.print10();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    myThread2.print15();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();
    }
}

class MyThread2 {
    Object o1 = new Object();
    int flag = 1;
    public void print5() throws InterruptedException {
        synchronized (o1) {
            while (flag != 1) {
                o1.wait();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            o1.notifyAll();
            flag = 2;
        }
    }

    public void print10() throws InterruptedException {
        synchronized (o1) {
            while (flag != 2) {
                o1.wait();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            o1.notifyAll();
            flag = 3;
        }
    }

    public void print15() throws InterruptedException {
        synchronized (o1) {
            while (flag != 3) {
                o1.wait();
            }
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            o1.notifyAll();
            flag = 1;
        }
    }
}
