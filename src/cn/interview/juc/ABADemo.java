package cn.interview.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/** 解决ABA问题:AtomicStampedReference
 * @author chancey
 * @create 2020-07-31 11:01
 */
public class ABADemo {
    private static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    //第二个参数当做版本号version
    private static AtomicStampedReference<Integer> atomicStampedReferenc = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {

        System.out.println("======ABA问题的产生======");
        new Thread(() -> {
            atomicReference.compareAndSet(100,101);
            atomicReference.compareAndSet(101,100);
        },"t1").start();

        new Thread(() -> {
            //暂停一秒钟保证t1线程完成ABA操作
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet(100, 2019) + "\t" + atomicReference.get());
        },"t2").start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("======ABA问题的解决======");

        new Thread(() -> {
            int stamp = atomicStampedReferenc.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 第1次版本号：" + stamp);
            //暂停一秒钟t3线程
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicStampedReferenc.compareAndSet(100,101,atomicStampedReferenc.getStamp(),atomicStampedReferenc.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t 第2次版本号：" + atomicStampedReferenc.getStamp());
            atomicStampedReferenc.compareAndSet(101,100,atomicStampedReferenc.getStamp(),atomicStampedReferenc.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t 第3次版本号：" + atomicStampedReferenc.getStamp());
        },"t3").start();

        new Thread(() -> {
            int stamp = atomicStampedReferenc.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 第1次版本号：" + stamp);
            //暂停3秒钟保证t3线程完成ABA操作
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean res = atomicStampedReferenc.compareAndSet(100, 2019, stamp, stamp + 1);

            System.out.println(Thread.currentThread().getName() + "\t 修改成功否：" + res + "\t 当前最新实际版本号：" + atomicStampedReferenc.getStamp());
            System.out.println(Thread.currentThread().getName() + "\t 当前最新实际值：" + atomicStampedReferenc.getReference());

        },"t4").start();
    }
}
