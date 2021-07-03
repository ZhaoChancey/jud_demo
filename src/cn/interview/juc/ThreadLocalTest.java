package cn.interview.juc;

/**
 * @author chancey
 * @create 2020-08-29 16:25
 */
public class ThreadLocalTest {
    static ThreadLocal<Integer> local = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public static void main(String[] args) {

        for (int i = 0; i <= 2; i++) {
            new Thread(() -> {
               add10ByThreadLocal();
            }).start();
        }
    }

    private static void add10ByThreadLocal() {
        for (int i = 0; i < 5; i++) {
            Integer n = local.get();
            n++;
            local.set(n);
            System.out.println(Thread.currentThread().getName() + "num为：" + local.get());
        }
    }
}
