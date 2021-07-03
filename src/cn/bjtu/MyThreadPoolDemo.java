package cn.bjtu;

import java.util.concurrent.*;

/**
 * @author chancey
 * @create 2020-07-16 21:22
 */
public class MyThreadPoolDemo {
    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());//查看CPU核数，8

        ExecutorService threadPool = new ThreadPoolExecutor(2,
                5,
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());//AbortPolicy直接中断

        try {
            //模拟6个顾客来银行办理业务，目前池子里面有5个工作人员提供服务
            for (int i = 1; i <= 10; i++) {
                threadPool.execute(() -> {//创建Runnable线程任务对象，即一个工作请求
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }

    }

    public void initPool() {
//        ExecutorService threadPool = Executors.newFixedThreadPool(5);//一池5个工作线程，类似一个银行有5个受理窗口
//        ExecutorService threadPool = Executors.newSingleThreadExecutor();//一池1个工作线程，类似一个银行有1个受理窗口
        ExecutorService threadPool = Executors.newCachedThreadPool();//一池N个工作线程，类似一个银行有N个受理窗口
        //执行很多短期异步任务，线程池根据需要创建新线程[如果任务量大且来的快，就多创建线程，如果任务量小且来的慢，就少创建几个],但在先前构建的线程可用时将重用它们。可扩容，遇强则强

        try {
            //模拟10个顾客来银行办理业务，目前池子里面有5个工作人员提供服务
            for (int i = 1; i <= 10; i++) {
                threadPool.execute(() -> {//创建Runnable线程任务对象，即一个工作请求
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
