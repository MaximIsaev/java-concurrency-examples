package concurrency;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {
    public static void main(String[] args) {
        Account account = new Account(0);

        Runnable writer = () -> {
            for (int i = 0; i < 20; i++) {
                account.deposit(100);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Runnable reader = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " got balance " + account.getBalance());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Thread writerThread = new Thread(writer, "Writer 1");
        Thread readerThread1 = new Thread(reader, "Reader 1");
        Thread readerThread2 = new Thread(reader, "Reader 2");

        writerThread.start();
        readerThread1.start();
        readerThread2.start();
    }
}


class Account {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    public void deposit(int amount) {
        lock.writeLock().lock();
        try {
            balance += amount;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int getBalance() {
        lock.readLock().lock();
        try {
            return balance;
        } finally {
            lock.readLock().unlock();
        }
    }
}


