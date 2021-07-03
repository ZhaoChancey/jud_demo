package cn.interview.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author chancey
 * @create 2020-08-02 12:51
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);//设定为容量是3

        /*
        第一组：抛异常
         */
        //当队列满再添加会报异常java.lang.IllegalStateException: Queue full
        /*System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
//        System.out.println(blockingQueue.add("d"));

        //element()检查队列是否为空，并且返回队首元素
        System.out.println(blockingQueue.element());

        //当队列为空再取出元素会报异常java.util.NoSuchElementException
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());

        //队列为空，用element方法检查会报异常
        System.out.println(blockingQueue.element());*/

         /*
        第二组：布尔值
         */
        //队列满添加失败就返回false
        /*System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("d"));

        //查看队首元素，检查是否为空
        System.out.println(blockingQueue.peek());

        //队列空再取返回null
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());

        System.out.println(blockingQueue.peek());//null*/

         /*
        第三组：阻塞
         */
         /*blockingQueue.put("a");
         blockingQueue.put("a");
         blockingQueue.put("a");
//         blockingQueue.put("a");

        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();*/

         /*
        第四组：超时控制
         */
        System.out.println(blockingQueue.offer("a", 3, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 3, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 3, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 3, TimeUnit.SECONDS));

    }
}
