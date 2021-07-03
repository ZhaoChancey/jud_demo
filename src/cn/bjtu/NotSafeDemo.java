package cn.bjtu;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xerces.internal.xs.StringList;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/** 举例说明集合类是不安全的
 *  1.故障现象：java.util.ConcurrentModificationException:并发修改异常
 *      ArrayList为线程不安全，多个线程对list同时读和写会出故障
 *  2.导致原因
 *
 *  3.解决方案
 *      3.1 Vector（不推荐）
 *      3.2 Collections.synchronizedList(new ArrayList<>())(将线程不安全的ArrayList转换为线程安全的list)
 *      3.3 CopyOnWriteArrayList【写时复制，读写分离】
 *  4.优化建议（同样的错误，不出现第二次）
 *      源码：
 *      /**
 *      public boolean add(E e){
        *final ReentrantLock lock=this.lock;
        *lock.lock();
        *try{
        *Object[]elements=getArray();
        *int len=elements.length;
        *Object[]newElements=Arrays.copyOf(elements,len+1);
        *newElements[len]=e;
        *setArray(newElements);
        *return true;
        *}finally{
        *lock.unlock();
        *}
        *}
        *
        *
        *
        *CopyOnWrite容器即写时复制的容器。往一个容器添加元素的时候，不直接往当前容器Object[]添加，
        *而是先将当前容器Object[]进行Copy，复制出一个新的容器Object[]newElements，然后向新的容器Object[]newElements里添加元素。
        *添加元素后，再将原容器的引用指向新的容器setArray(newElements)。
        *这样做的好处是可以对CopyOnWrite容器进行并发的读，而不需要加锁，因为当前容器不会添加任何元素。
        *所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器。
 * @author chancey
 * @create 2020-07-16 9:27
 */
public class NotSafeDemo {
    public static void main(String[] args) {
//        List<String> list = Collections.synchronizedList(new ArrayList<>());
        /*
        针对HashMap线程不安全的措施：
            1.Collections.synchronizedMap()
            2. ConcurrentHashMap
         */
//        Map<String, String> map = new ConcurrentHashMap();
//
//        for (int i = 0; i < 30; i++) {
//            new Thread(() -> {
//                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0,8));
//                System.out.println(map);
//            }, String.valueOf(i)).start();
//        }
        listNotSafe();

    }

    public static void listNotSafe(){
        List<String> list = new CopyOnWriteArrayList<>();

        //只有一个main线程时不会报错
//        list.add("A");
//        list.add("B");
//        list.add("C");
//        for (String s : list) {
//            System.out.println(s);
//        }

        /**
         * java.util.ConcurrentModificationException高并发时出现的异常
         */
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }

    public static void setNotSafe(){
        /*
        针对HashSet线程不安全的措施：
            1.Collections.synchronizedSet
            2. CopyOnWriteArraySet
         */
//        Set<String> set = Collections.synchronizedSet(new HashSet<>());
        Set<String> set = new CopyOnWriteArraySet<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0,8));//vector的add方法是同步的，但是println方法不是同步的，所以打印出的结果数量不一
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }
}
