package cn.bjtu.entity;

import lombok.SneakyThrows;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chancey
 * @create 2020-09-21 18:31
 */
public class PrintA_Z {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        new Thread(myThread).start();
        new Thread(myThread).start();
    }
}

class MyThread implements Runnable{
    int num = 0;
    Object object = new Object();

    @Override
    public void run() {
        while (true) {
            synchronized (object) {
                object.notify();
                if (num <= 25) {
                    System.out.println((char)(num++ + 'A'));
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            }
        }
    }
}
