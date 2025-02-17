package concurrency;

import java.util.concurrent.locks.ReentrantLock;

public class DeadlockExample {
    public static void main(String[] args) {
        DeadLockFree deadLock = new DeadLockFree();

        Thread t1 = new Thread(deadLock::method1);
        Thread t2 = new Thread(deadLock::method2);

        t1.start();
        t2.start();
    }
}

class DeadLock {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void method1() {
        synchronized (lock1) {
            System.out.println(Thread.currentThread() + " got lock 1, wait lock 2");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (lock2) {
                System.out.println(Thread.currentThread() + " got lock 2");
            }
        }
    }

    public void method2() {
        synchronized (lock2) {
            System.out.println(Thread.currentThread() + " got lock 2, wait lock 1");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (lock1) {
                System.out.println(Thread.currentThread() + " got lock 1");
            }
        }
    }
}

class DeadLockFree {
    private final ReentrantLock lock1 = new ReentrantLock();
    private final ReentrantLock lock2 = new ReentrantLock();

    public void method1() {
        if (lock1.tryLock()) {
            try {
                System.out.println(Thread.currentThread() + " got lock 1, wait lock 2");

                if (lock2.tryLock()) {
                    try {
                        System.out.println(Thread.currentThread() + " got lock 2");
                    } finally {
                        lock2.unlock();
                    }
                } else {
                    System.out.println(Thread.currentThread() + " cannot get lock 2");
                }
            } finally {
                lock1.unlock();
            }
        }
    }

    public void method2() {
        if (lock2.tryLock()) {
            try {
                System.out.println(Thread.currentThread() + " got lock 2, wait lock 1");

                if (lock1.tryLock()) {
                    try {
                        System.out.println(Thread.currentThread() + " got lock 1");
                    } finally {
                        lock1.unlock();
                    }
                } else {
                    System.out.println(Thread.currentThread() + " cannot get lock 1");
                }
            } finally {
                lock2.unlock();
            }
        }
    }
}
