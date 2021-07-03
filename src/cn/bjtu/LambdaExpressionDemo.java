package cn.bjtu;

/**
 * @author chancey
 * @create 2020-07-15 15:20
 */

/**
 * 接口只有一个方法称为函数式接口
 */
@FunctionalInterface // 当接口只有一个方法(默认方法除外)时，即使不加这个注解，系统也会默认加上【函数式接口可有多个默认方法、多个静态方法】
interface Foo{
//    public void sayHello();
    public int add(int x, int y);

    default int div(int x, int y){
        System.out.println("111222");
        return x / y;
    }

//    default int div2(int x, int y){
//        System.out.println("111222");
//        return x / y;
//    }
    public static int mv(int x, int y) {
        return x * y;
    }
}

/**
 * 2 lambda表达式：
 *      2.1 口诀：拷贝小括号、写死右箭头、落地大括号
 *      2.2 @FunctionalInterface
 *      2.3 default：方法的默认实现
 *          java8之后允许接口里有部分方法的实现
 *      2.4 静态方法实现
 */
public class LambdaExpressionDemo {
    public static void main(String[] args) {
        /*Foo foo = new Foo() {
            @Override
            public void sayHello() {
                System.out.println("***hello*****");
            }
        };
        foo.sayHello();*/
//        Foo foo = () -> {System.out.println("***hello lambda*****");};
//        foo.sayHello();

        Foo foo = (x, y) -> {
            System.out.println("hello return ");
            return x + y;
        };
        System.out.println(foo.add(1, 5));

        System.out.println(foo.div(10, 5));

        Foo.mv(3, 5);
    }
}
