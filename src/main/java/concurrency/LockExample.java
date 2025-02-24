package concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample {
    public static void main(String[] args) {
        System.out.println("Start decrease the money");
        BankAccount account = new BankAccount(10000);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 30; i++) {
                try {
                    if (!account.isEnoughMoney(600)) {
                        break;
                    }
                    account.decreaseMoney(600);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Thread 1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                try {
                    if (!account.isEnoughMoney(300)) {
                        break;
                    }
                    account.decreaseMoney(300);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Thread 2");

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 70; i++) {
                try {
                    if (!account.isEnoughMoney(150)) {
                        break;
                    }
                    account.decreaseMoney(150);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Thread 3");

        t1.start();
        t2.start();
        t3.start();
    }
}

class BankAccount {
    private long money;

    private final Lock lock;

    public BankAccount(long money) {
        this.money = money;
        this.lock = new ReentrantLock();
    }

    public long getBalance() {
        return money;
    }

    public boolean isEnoughMoney(long value) {
        if (lock.tryLock()) {
            try {
                if (money - value >= 0) {
                    return true;
                } else {
                    System.out.println(Thread.currentThread().getName() + " - not enough money to get. Stopped.");
                    return false;
                }
            } finally {
                lock.unlock();
            }
        } else {
            return true;
        }
    }

    public void decreaseMoney(long value) {
        if (lock.tryLock()) {
            try {
                long result = money - value;
                if (result >= 0) {
                    money = result;
                }
                System.out.println(Thread.currentThread().getName() + " got " + value + ". Current balance = " + getBalance());
            } catch (Exception e) {
                System.out.println("Exception occurred " + e.getMessage());
            } finally {
                lock.unlock();
            }
        }
    }
}
