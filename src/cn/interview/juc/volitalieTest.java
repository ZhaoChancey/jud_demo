package cn.interview.juc;

/**
 * @author chancey
 * @create 2020-08-28 18:21
 */
public class volitalieTest {
    public static void main(String[] args) {
        Bank bank = new Bank();

        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    bank.addMoney(10);
                }
            }).start();
        }
        System.out.println(Thread.currentThread().getName() + "余额为" + bank.getMoney());
    }
}

class Bank{
    private volatile int money = 0;

    public int getMoney() {
        return money;
    }

    public void addMoney(int money) {
//        this.money += money;
        this.money++;
    }
}
