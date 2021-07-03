//package cn.bjtu;
//
///**
// * @author chancey
// * @create 2020-09-21 14:03
// */
//public class Main implements Runnable{
//    int i = 100;
//    Object obj = new Object();
//
//    public synchronized void take() {
//        try {
//            obj.wait();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        if (i > 0) {
//            System.out.println(Thread.currentThread().getName() + ":" +i);
//            i--;
//        }
//        obj.notify();
//    }
//
//    public synchronized void put() {
//        try {
//            obj.wait();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        if (i > 0) {
//            System.out.println(Thread.currentThread().getName() + ":" +i);
//            i++;
//        }
//        obj.notify();
//    }
//    public static void main(String[] args) {
//        new Thread()
//    }
//
//    @Override
//    public void run() {
//
//    }
//}
