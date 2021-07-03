package cn.bjtu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author chancey
 * @create 2020-07-17 18:11
 *
 *
 */
class User {
    private Integer id;
    private String username;
    private Integer age;

    public User(Integer id, String username, Integer age) {
        this.id = id;
        this.username = username;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
/*题目:请按照给出数据，找出同时满足以下条件的用户,也即以下条件全部满足:
        * 偶数ID
        * 且年龄大于24
        * 且用户名转为大写
        * 且用户名字母倒排序
        * 只输出一个用户名字*/
public class StreamDemo{
    public static void main(String[] args) {
        User u1 = new User(11,"a",23);
        User u2 = new User(12,"b",24);
        User u3 = new User(13,"c",22);
        User u4 = new User(14,"d",28);
        User u5 = new User(16,"e",26);

        List<User> list = Arrays.asList(u1, u2, u3, u4, u5);
        //list.stream()返回一个流，数据源;user就是list里存放的user对象
        list.stream().filter(user -> {return user.getId() % 2 == 0;}).
                filter(user -> {return user.getAge() > 24;}).
                map(user -> {return user.getUsername().toUpperCase();}).
                sorted(((o1, o2) -> {return o2.compareTo(o1);})).
                limit(1).
                forEach(System.out::println);

        /*//map()用法,将list的元素乘以2；collect(Collectors.toList())把流转换为list
        List<Integer> list2 = Arrays.asList(1,2,3);
        list2 = list2.stream().map(integer -> {return integer * 2;}).collect(Collectors.toList());*/


        //=================================================
        //=================================================
        //=================================================
        /*//匿名内部类形式,Function<T,R>函数型接口：此类型的接口有一个输入参数，有一个返回参数
//        Function<String, Integer> function = new Function<String, Integer>() {
//            @Override
//            public Integer apply(String s) {
//                return 1024;
//            }
//        };
        //只有一个形参可以省略小括号
        Function<String, Integer> function = s -> {return s.length();};
        System.out.println(function.apply("aaa"));

        //断定型接口：匿名内部类形式
        *//*Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return false;
            }
        };*//*
        Predicate<String> predicate = s -> {return s.isEmpty();};
        System.out.println(predicate.test("111"));

        //消费型接口：匿名内部类形式
        *//*Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {

            }
        };*//*
        Consumer<String> consumer = s -> {
            System.out.println(s);
        };
        consumer.accept("111");

        //供给型接口：匿名内部类形式
       *//* Supplier<String> supplier = new Supplier<String>() {
            @Override
            public String get() {
                return null;
            }
        };*//*
       Supplier<String> supplier = () -> {
           return "11212";
       };
        System.out.println(supplier.get());*/
    }
}

//该接口的方法都是有一个输入参数，有一个返回参数，可以抽取出来
interface MyInterface{
    public int myInt(int x);
    public String myString(String str);
}

