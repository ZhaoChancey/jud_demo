package cn.bjtu;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/** 多线程中，第三种获得多线程的方式
 *
 * 1.get方法一般放在最后一行
 * @author chancey
 * @create 2020-07-16 10:55
 */
//泛型里写的类型就是call()的返回值类型
class MyThread implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        TimeUnit.SECONDS.sleep(4);
        System.out.println(Thread.currentThread().getName() + "***您来啦");
        return 1024;
    }
}
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //public Thread(Runnable target)，Thread构造器只能放Runnable接口的实现类，要找一个与Runnable和Callable接口都有关系的实现类
        //FutureTask既实现了Runnable接口，又能
        FutureTask task = new FutureTask(new MyThread());
        FutureTask task1 = new FutureTask(new MyThread());

        new Thread(task, "A").start();
        new Thread(task, "B").start();//同一个FutureTask对象，不管几个线程只会调用一次
        new Thread(task1, "C").start();//不同的FutureTask对象，可以多次调用


        System.out.println(Thread.currentThread().getName() + "计算完成");

        System.out.println(task.get());//从task里获得返回值

    }
}
