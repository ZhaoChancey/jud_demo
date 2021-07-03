package cn.interview.juc;

/**
 * @author chancey
 * @create 2020-07-30 15:02
 */
public class SingletonDemo {
    private volatile static SingletonDemo instance = null;

    private SingletonDemo(){
        System.out.println(Thread.currentThread().getName() + "\t 构造方法调用了");
    }

    //DCL模式 Double Check Lock 双端检索机制：在加锁前后都进行判断
    public static SingletonDemo getInstance(){
        if (instance == null) {
            synchronized (SingletonDemo.class) {
                if (instance == null) {
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }
    public static void main(String[] args) {
        //单线程（main线程的操作动作）
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());

        //并发多线程
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                SingletonDemo.getInstance();
            }, String.valueOf(i)).start();
        }
    }
}
