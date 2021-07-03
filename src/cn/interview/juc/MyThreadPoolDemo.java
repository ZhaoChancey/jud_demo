package cn.interview.juc;

import java.util.concurrent.*;

/** 第四种使用Java多线程的方式，线程池
 * @author chancey
 * @create 2020-08-03 9:34
 */
public class MyThreadPoolDemo {
    public static void main(String[] args) {
        //查看CPU核数
//        System.out.println(Runtime.getRuntime().availableProcessors());
        //手写线程池
        //上限请求数为最大值+阻塞队列容量
        ExecutorService threadPool = new ThreadPoolExecutor(2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        //CallerRunsPolicy:如果线程池同时处理不了多个请求，多出来的请求回退到调用者（线程池是在main方法中调用的），即main线程来处理

        //模拟10个用户来办理业务，每个用户就是一个请求线程
        try {
            for (int i = 1; i <= 50; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    public void ThreadPoolInit() {
        //用Executors获取线程池
        //        ExecutorService threadPool = Executors.newFixedThreadPool(5);//一池5个线程
//        ExecutorService threadPool = Executors.newSingleThreadExecutor();//一池1个线程
        ExecutorService threadPool = Executors.newCachedThreadPool();//一池N个线程

        //一般常用try-catch-finally
        //模拟10个用户来办理业务，每个用户就是一个请求线程
        try {
            for (int i = 1; i <= 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
                TimeUnit.MILLISECONDS.sleep(20);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
