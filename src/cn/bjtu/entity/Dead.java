package cn.bjtu.entity;

/**
 * @author chancey
 * @create 2020-09-22 9:19
 */
public class Dead {
    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = new Object();

        new Thread(() -> {
            synchronized (o1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "拿到o1");
                synchronized (o2) {
                    System.out.println(Thread.currentThread().getName() + "拿到o2");
                }
            }
        }, "A").start();

        new Thread(() -> {
            synchronized (o2) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "拿到o2");
                synchronized (o1) {
                    System.out.println(Thread.currentThread().getName() + "拿到o1");
                }
            }
        }, "B").start();
    }
}
