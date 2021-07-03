package cn.interview.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/** 1.验证volatile的可见性
 * 1.1 假设int number = 0; number变量还没有添加volatile关键字，不满足可见性
 * 1.2 添加了volatile，可以解决可见性问题
 *
 *  2.验证volatile不满足原子性
 *      2.1 原子性：代表最终一致性能不能保证
 *          不可分割，完整性。即某个线程在做某个业务时，中间不可以被加塞或者被分割，需要整体完整
 *          要么同时成功，要么同时失败
 *      2.2 案例演示
 *
 *      2.3 why
 *          【解释】输出结果1的解释：当线程1执行Num++;语句时，先是读入Num的值为0，
 *          倘若此时线程1让出CPU执行权，线程2获得执行，线程2会重新从主内存中，读入Num的值还是0，
 *          然后线程2执行+1操作，最后把Num=1刷新到主内存中； 线程2执行完后，线程1由开始执行，
 *          但之前已经读取的Num的值0，所以它还是在0的基础上执行+1操作，也就是还是等于1，并刷新到主内存中。所以最终的结果是1
 *       2.4 如何解决
 *          1.sync
 *          2.使用juc包下的AtomicInteger
 * @author chancey
 * @create 2020-07-30 10:07
 */
public class VolatileDemo {
    public static void main(String[] args) {
        MyData myData = new MyData();
        boolean flag = true;
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    myData.addPlus();
                    myData.addMyAtom();
                }
            },String.valueOf(i)).start();
        }

        //需要等待上面20个线程全部计算完成后，再用main线程取得最终的结果看是多少
        while (Thread.activeCount() > 2) { //一般程序启动有两个线程：主线程和垃圾回收线程，只要大于2个说明还有其他线程工作，就得让出执行权
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName()+"\t int type finally number value: "+myData.number);
        System.out.println(Thread.currentThread().getName()+"\t Atom type finally number value: "+myData.atomicInteger);
//        seekOkVolatils();
    }

    //volatile可以保证可见性，及时通知其它线程主物理内存的值已被修改
    public static void seekOkVolatils(){
        MyData myData = new MyData();//资源类

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName()+"\t update number value: "+ myData.number);
        },"AAA").start();

        //第二个线程就是main主线程
        while (myData.number == 0) {
            //main线程持有共享数据的拷贝，一直为0
        }
        System.out.println(Thread.currentThread().getName() + "\t mission is over, number value:" + myData.number);
    }
}
class MyData{
//    int number = 0;
    volatile int number = 0;
    public void addTo60(){
        this.number = 60;
    }

    //此时number前面已经加了volatile，但是不保证原子性
    public void addPlus(){
        number++;
    }

    //使用原子类
    AtomicInteger atomicInteger = new AtomicInteger();

    public void addMyAtom(){
        atomicInteger.getAndIncrement();
    }
}
