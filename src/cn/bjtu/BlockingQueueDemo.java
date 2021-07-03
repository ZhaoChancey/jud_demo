package cn.bjtu;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author chancey
 * @create 2020-07-16 17:12
 * 阻塞队列
 * 1 两个数据结构：栈/队列
 *      1.1 栈       后进后出
 *      1.2 队列      先进先出
 *      1.3 总结
 *
 *  2. 阻塞队列
 *      2.1 阻塞  必须要阻塞/不得不阻塞
 *
 *  3.how
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
//        Collection collection = null;
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        //抛出异常：
        /*System.out.println(blockingQueue.add("A"));
        System.out.println(blockingQueue.add("B"));
        System.out.println(blockingQueue.add("C"));
//        System.out.println(blockingQueue.add("D"));//Queue full

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());//NoSuchElementException*/

        /*System.out.println(blockingQueue.add("A"));
        System.out.println(blockingQueue.add("B"));
        System.out.println(blockingQueue.element());//检查队列里是否有数据*/

        //特殊值：指返回Boolean值
        /*System.out.println(blockingQueue.offer("A"));
        System.out.println(blockingQueue.offer("B"));
        System.out.println(blockingQueue.offer("C"));
//        System.out.println(blockingQueue.offer("X"));//false

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());//null*/

        //阻塞
        /*blockingQueue.put("a");
        blockingQueue.put("a");
        blockingQueue.put("a");
//        blockingQueue.put("a");//当阻塞队列满时，生产者线程继续往队列里put元素，队列会一直阻塞生产者线程直到队列有空位为止

        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
//        System.out.println(blockingQueue.take());*/

        //超时
        System.out.println(blockingQueue.offer("A"));
        System.out.println(blockingQueue.offer("B"));
        System.out.println(blockingQueue.offer("C"));
        System.out.println(blockingQueue.offer("A", 3, TimeUnit.SECONDS));

    }
}
