package cn.bjtu.entity;

/**
 * @author chancey
 * @create 2020-09-23 22:24
 */
public class ABAB {
    public static void main(String[] args) {
        Print print = new Print();
        new Thread(() -> {
            print.printA();
        }).start();

        new Thread(() -> {
            print.printB();
        }).start();
    }
}

class Print{
    public synchronized void printA() {
        while (true) {
            this.notify();
            System.out.println("A");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void printB() {
        while (true) {
            this.notify();
            System.out.println("B");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
