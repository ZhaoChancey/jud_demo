package cn.interview.juc;

import java.util.concurrent.atomic.AtomicInteger;

/** 1.CAS:比较并交换
 * @author chancey
 * @create 2020-07-30 15:45
 */
public class CASDemo {
    public static void main(String[] args) {
        //主内存的值默认为5
        AtomicInteger atomicInteger = new AtomicInteger(5);

        System.out.println(atomicInteger.compareAndSet(5, 2019) + "\t current val : " + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 1024) + "\t current val : " + atomicInteger.get());

        atomicInteger.getAndIncrement();
    }
}
