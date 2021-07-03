package cn.interview.juc;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/** java.util.ConcurrentModificationException
 * @author chancey
 * @create 2020-07-31 12:28
 */
public class ContainerNotSafeDemo {
    public static void main(String[] args) {
        Map<String, String> hashMap = new ConcurrentHashMap<>()/*new HashMap<>()*/;
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                hashMap.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0,8));
                System.out.println(hashMap);
            }, String.valueOf(i)).start();
        }
    }

    public static void listNotSafe(){
        //1.用Vector，底层方法加的锁sync（不推荐）
        //        List<String> list = new Vector<>(); /*new ArrayList<>();*/

        //2.Collections.synchronizedList
//        List<String> list = Collections.synchronizedList(new ArrayList<>());

        //3.CopyOnWriteArrayList
        List<String> list = new CopyOnWriteArrayList<>();

        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
        //java.util.ConcurrentModificationException

        /**
         * 1. 故障：java.util.ConcurrentModificationException
         *
         * 2.原因：看印象笔记
         *
         * 3.解决方案
         *  1.用Vector
         *  2.Collections.synchronizedList(new ArrayList<>())
         *  3.CopyOnWriteArrayList
         *
         * 4.优化建议
         */
    }

    public void setNotSafe(){
        //java.util.ConcurrentModificationException
        Set<String> set = new CopyOnWriteArraySet<>()   /*Collections.synchronizedSet(new HashSet<>())*//* new HashSet<>()*/;

        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }
}
