package cn.bjtu.entity;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author chancey
 * @create 2020-07-17 21:45
    分之合并框架

    ForkJoinPool
    ForkJoinTask
    RecusiveTask
 */
//资源类
class MyTask extends RecursiveTask<Integer> {

    private final int ADJUST_VALUE = 10;

    private int start;
    private int end;
    private int res;

    public MyTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if ((end - start) <= ADJUST_VALUE) {
            for (int i = start; i <= end; i++) {
                res = res + i;
            }
        } else {
            int middle = (start + end) / 2;
            MyTask task1 = new MyTask(start, middle);
            MyTask task2 = new MyTask(middle + 1, end);
            task1.fork();//调用fork相当于递归回来调用compute（）
            task2.fork();
            res = task1.join() + task2.join();//最后将分支的结果合在一起
        }
        return res;
    }
}

public class ForkJoinDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyTask myTask = new MyTask(0, 10000);

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> task = forkJoinPool.submit(myTask);//提交任务，返回计算的结果
        System.out.println(task.get());

        forkJoinPool.shutdown();
    }
}
