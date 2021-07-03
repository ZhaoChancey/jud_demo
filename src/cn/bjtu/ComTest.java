package cn.bjtu;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chancey
 * @create 2020-09-10 12:24
 */
public class ComTest {
    public static void main(String[] args) {
        Share share = new Share();
        new Thread(() -> {
            try {
                share.print1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();

        new Thread(() -> {
            try {
                share.print2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();
    }
}

class Share{
    Object object = new Object();
    int i = 0;
    int j = 0;
    public void print1() throws InterruptedException {
        synchronized (object) {
            while (i < 52) {
                object.notify();
                System.out.printf("%d%d", ++i, ++i);
                if (i == 52) {
                    break;
                }
                object.wait();
            }
        }
    }

    public void print2() throws InterruptedException {
        synchronized (object) {
            while (j <= 26) {
                object.notify();
                System.out.print((char)('A' + j++));
                if (j == 26) {
                    break;
                }
                object.wait();
            }
        }
    }
}
