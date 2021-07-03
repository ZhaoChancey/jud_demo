package cn.bjtu.entity;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author chancey
 * @create 2020-07-17 22:49
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //runAsync是异步调用，只是有一个线程处理任务，但没有返回值
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "没有返回值， update");
        });
        voidCompletableFuture.get();

        //有返回值的异步回调
        //如果把任务比喻为烧水，没有回调时就只能守着水壶等待水开，有了回调相当于换了一个会响的水壶，
        // 烧水期间可用作其他的事情，等待水开了水壶会自动发出声音，这时候再回来处理。水壶自动发出声音就是回调。
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("有返回值哦");
            int a = 1 / 0;
            return 1024;//比如学生问老师题老师正忙着，学生把题目写到纸上跟老实说什么时候解出来再告诉他，等老师解出来打电话告诉学生的过程就称为异步回调
        });

        System.out.println(integerCompletableFuture.whenComplete((t, u) -> {
            //如果回调成功则走这一步
            System.out.println("****t" + t);
            System.out.println("****u" + u);
        }).exceptionally(e -> {
            //异常走这
            System.out.println("***exception" + e.getMessage());
            return 444;
        }).get());

    }
}
