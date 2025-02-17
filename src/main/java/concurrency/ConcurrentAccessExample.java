package concurrency;


//test concurrent access to variable
public class ConcurrentAccessExample {
    public static void main(String[] args) throws InterruptedException {
        SafeBankAccount account = new SafeBankAccount();

        Thread user1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                account.deposit(50);
            }
        });

        Thread user2 = new Thread(() -> {
            for (int i = 0; i < 300; i++) {
                account.withdraw(100);
            }
        });

        user1.start();
        user2.start();

        user1.join();
        user2.join();

        System.out.println("Final balance " + account.getMoney());
    }
}

class SafeBankAccount {

    private final Object lock = new Object();

    private int money = 0;

    public void deposit(int amount) {
        synchronized (lock) {
            money = money + amount;
            System.out.println("Deposit money = " + getMoney());
        }
    }

    public void withdraw(int amount) {
        synchronized (lock) {
            int result = money - amount;
            if (result > 0) {
                money = result;
                System.out.println("Withdraw money = " + getMoney());
            } else {
                System.out.println(Thread.currentThread().getName() + " withdraw not enough money " + getMoney());
            }
        }
    }

    public int getMoney() {
        return money;
    }
}
