package cn.bjtu;

/**
 * @author chancey
 * @create 2020-09-10 14:57
 */
public class SingletonAgain {

    private volatile static SingletonAgain singletonAgain;

    private SingletonAgain() {};

    public static SingletonAgain getInstance() {
        if (singletonAgain == null) {
            synchronized (SingletonAgain.class) {
                if (singletonAgain == null) {
                    singletonAgain = new SingletonAgain();
                }
            }
        }
        return singletonAgain;
    }
}
