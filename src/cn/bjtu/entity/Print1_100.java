package cn.bjtu.entity;

/**
 * @author chancey
 * @create 2020-09-21 18:41
 */
public class Print1_100 {
    public static void main(String[] args) {
        MyThread1 myThread1 = new MyThread1();
        new Thread(() -> {
            myThread1.incr();
        }).start();

        new Thread(() -> {
            myThread1.incr();
        }).start();
    }
}

class MyThread1{
    int num = 1;
    Object object = new Object();

    public void incr() {
        while (true) {
            synchronized (object) {
                object.notify();
                if (num <= 100) {
                    System.out.println(num++);
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
