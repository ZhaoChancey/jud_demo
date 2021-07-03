package cn.bjtu;

import java.util.concurrent.TimeUnit;

/**
 * @author chancey
 * @create 2020-07-15 21:26
 */
class Phone{

    //加了static,锁的不是this对象，而是Phone.class这个模板
    public static synchronized void sendEmail(){
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("---sendEmail");
    }

    public static synchronized void sendMS(){
        System.out.println("---sendMS");
    }

    public void hello(){
        System.out.println("----hello");
    }
}

/**
 * 题目：多线程8锁
 * 1 标准访问[两个普通的synchronized方法]，请问先打印邮件还是短信？ 主线程中加入Thread.sleep(200);邮件
 * 2.邮件方法暂停4秒钟，请问先打印邮件还是短信？ TimeUnit.SECONDS.sleep(4);邮件
 * 3.新增一个普通方法hello()，请问先打印邮件还是hello【不发短信】？hello
 * 4.现在有两部手机，先打印短信还是邮件？ 短信
 * 5.两个静态同步方法，1部手机，先打印短信还是邮件? 邮件
 * 6.两个静态同步方法，2部手机，先打印短信还是邮件？邮件
 * 7. 1个静态同步方法，1个普通同步方法，1部手机，先打印短信还是邮件?短信
 * 8. 1个静态同步方法，1个普通同步方法，2部手机，先打印短信还是邮件？短信
 *
 * 解释：
 * 1、2：一个对象里面如果有多个synchronized方法，某一个时刻内，只要一个线程去调用其中的一个synchronized方法了，
 * 其它的线程都只能等待，换句话说，某一个时刻内，只能有唯一一个线程去访问这些synchronized方法
 * 锁的是当前对象this，被锁定后，其它的线程都不能进入到当前对象的其它的synchronized方法
 *
 * 3：加个普通方法后发现和同步锁无关，synchronized方法和不加synchronized的方法无关
 * 4：换成两个对象后，不是同一把锁了，情况立刻变化。
 *
 * 都换成静态同步方法后，情况又变化
 * new this ，具体的一部部手机
 * 静态 class ， 唯一的一个模板
 *
 * synchronized实现同步的基础：Java中的每一个对象都可以作为锁。
 * 具体表现为以下3种形式。
 * 对于普通同步方法，锁是当前实例对象。
 * 对于静态同步方法，锁是当前类的Class对象。
 * 对于同步方法块，锁是Synchonized括号里配置的对象
 *
 * 当一个线程试图访问同步代码块时，它首先必须得到锁，退出或抛出异常时必须释放锁。
 *
 * 也就是说如果一个实例对象的非静态同步方法获取锁后，该实例对象的其他非静态同步方法必须等待获取锁的方法释放锁后才能获取锁，
 * 可是别的实例对象的非静态同步方法因为跟该实例对象的非静态同步方法用的是不同的锁，
 * 所以毋须等待该实例对象已获取锁的非静态同步方法释放锁就可以获取他们自己的锁。
 *
 * 所有的静态同步方法用的也是同一把锁——类对象本身，
 * 这两把锁（Class/this）是两个不同的对象，所以静态同步方法与非静态同步方法之间是不会有竞态条件的。
 * 但是一旦一个静态同步方法获取锁后，其他的静态同步方法都必须等待该方法释放锁后才能获取锁，
 * 而不管是同一个实例对象的静态同步方法之间，
 * 还是不同的实例对象的静态同步方法之间，只要它们同一个类的实例对象！
 */
public class Lock8 {
    //InterruptedException 线程的中断异常

    /**
     * 在主线程中并不是按照先sendEmail()再sendMS()的顺序调用，线程调度是由操作系统决定的
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Phone phone = new Phone();
        Phone phone2 = new Phone();

        new Thread(() -> {
            try {
                phone.sendEmail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "A").start();

//        Thread.sleep(200);
        new Thread(() -> {
            try {
//                phone.sendMS();
//                phone.hello();
//                phone2.sendMS();
                phone2.sendMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "B").start();
    }
}
