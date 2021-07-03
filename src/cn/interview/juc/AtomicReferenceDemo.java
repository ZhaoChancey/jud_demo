package cn.interview.juc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author chancey
 * @create 2020-07-31 10:40
 */
public class AtomicReferenceDemo {
    public static void main(String[] args) {
        User z3 = new User("z3", 22);
        User li4 = new User("li4", 25);

        //存放在主内存，AtomicReference<>用于对某个类进行原子包装
        AtomicReference<User> userAtomicReference = new AtomicReference<>();
        //此时主内存中的共享变量为原子类存放的z3
        userAtomicReference.set(z3);

        System.out.println(userAtomicReference.compareAndSet(z3, li4) + "\t" + userAtomicReference.get());
        System.out.println(userAtomicReference.compareAndSet(z3, li4) + "\t" + userAtomicReference.get());
    }
}

class User{
    String username;
    int age;

    public User(String username, int age) {
        this.username = username;
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
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
