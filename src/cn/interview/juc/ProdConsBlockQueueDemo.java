package cn.interview.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/** volatile/CAS/AtomicInteger/BlockQueue/线程交互/原子引用
 * @author chancey
 * @create 2020-08-02 16:43
 */
public class ProdConsBlockQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<String>(10));
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t生产线程启动");
            try {
                myResource.myProd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Producer").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t消费线程启动");
            System.out.println();
            try {
                myResource.myConsu();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Consumer").start();

        TimeUnit.SECONDS.sleep(5);

        myResource.stop();
    }
}

class MyResource{
    private volatile boolean FLAG = true;//默认开启，进行生产+消费
    private AtomicInteger atomicInteger = new AtomicInteger();

    BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void myProd() throws InterruptedException {
        String data = null;
        boolean resVal;
        while (FLAG) {
            data = atomicInteger.incrementAndGet() + "";
            resVal = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (resVal) {
                System.out.println(Thread.currentThread().getName() + "\t" + "插入队列" + data + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t" + "插入队列" + data + "失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + "\tFLAG==false，停止生产");
    }

    public void myConsu() throws InterruptedException {
        String res = null;
        while (FLAG) {
            res = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (res == null || "".equalsIgnoreCase(res)) {
                FLAG = false;
                System.out.println(Thread.currentThread().getName() + "\t超过2秒钟没有消费，退出消费");
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t消费队列" + res + "成功");
        }
    }

    public void stop() {
        this.FLAG = false;
    }
}
