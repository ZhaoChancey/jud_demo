package cn.interview.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/** 第三种获得线程的方式
 * @author chancey
 * @create 2020-08-02 19:01
 */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //两个线程，一个main线程，一个是AA线程

        /*public FutureTask(Callable<V> callable) {*/
        //泛型写实现Callable接口的泛型类
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new MyThread());
        FutureTask<Integer> futureTask2 = new FutureTask<Integer>(new MyThread());
        /*Thread的构造方法只能传Runnable接口的实现类，而FutureTask类实现的接口为RunnableFuture，而RunnableFuture接口为
        Runnable接口的子接口，所以FutureTask也是Runnable的实现类，恰好FutureTask的构造器可以传入Callable接口的实现类，
        这样就把Runnable接口与Callable接口联系起来*/

        //①多个线程争抢一个futureTask，只计算（执行）一次
        //②要计算多次，用多个futureTask
        new Thread(futureTask, "A").start();
        new Thread(futureTask2, "B").start();
//        int res02 = futureTask.get();

        System.out.println(Thread.currentThread().getName() + "************");

        int res01 = 100;
        //获得Callable线程的计算结果，若没有计算完成就要去强求，会导致阻塞，直到计算完成，因此建议放最后
        int res02 = futureTask.get();
        System.out.println(res01 + res02);

    }
}

class MyThread implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() +  "**********come in callable");
        TimeUnit.SECONDS.sleep(3);
        return 1024;
    }
}
