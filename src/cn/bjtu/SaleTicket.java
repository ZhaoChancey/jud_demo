package cn.bjtu;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chancey
 * @create 2020-07-11 20:44
 */
// 资源类
class Ticket {

    private int number = 30;
    private Lock lock = new ReentrantLock();
    public void saleTicket(){
        //局部细粒度的加锁控制
        lock.lock();
        try {
            if (number > 0){
                Thread.sleep(300);
                System.out.println(Thread.currentThread().getName() + "\t卖出第：" + number-- + "\t 还剩下：" + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
//        if (number > 0){
////            try {
//            // 加上这一行后就会出现线程安全问题需要加锁解决
////                Thread.sleep(300);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
//            System.out.println(Thread.currentThread().getName() + "\t卖出第：" + number-- + "\t 还剩下：" + number);
//        }
    }
}
/**
 * 题目： 三个售票员  卖出   30张票
 *
 * 多线程编程的企业级套路+模板
 *
 * 1.   在高内聚低耦合的前提下： 线程    操作（对外暴露的调用方法）     资源类
 *      高内聚：资源类把外界操作自己的方法即对外提供的业务功能，紧紧放在自己身上【两人用一个空调，制冷还是制热的方法功能是放在空调身上定义    的】
 *      低耦合：例子：空调和人不同，制冷制热的功能是空调说了算，跟人没关系，零耦合
 */
public class SaleTicket {
    public static void main(String[] args) {  // main是一切程序入口

        Ticket ticket = new Ticket();

        //用lambda 表达式
        new Thread(() -> {for (int i = 0; i <= 30; i++) ticket.saleTicket(); },"A").start();
        new Thread(() -> {for (int i = 0; i <= 30; i++) ticket.saleTicket(); },"B").start();
        new Thread(() -> {for (int i = 0; i <= 30; i++) ticket.saleTicket(); },"C").start();
//        Thread thread = new Thread();//新建状态
//        thread.start();//就绪状态，并不立刻调用run方法进入运行状态，是由CPU和操作系统的底层调度来决定的

        //Thread(Runnable target, String name)
        //匿名内部类
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 40; i++) {
                    ticket.saleTicket();
                }
            }
        },"A").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 40; i++) {
                    ticket.saleTicket();
                }
            }
        },"B").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 40; i++) {
                    ticket.saleTicket();
                }
            }
        },"C").start();*/


    }
}
