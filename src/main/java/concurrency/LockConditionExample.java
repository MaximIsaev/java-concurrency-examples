package concurrency;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockConditionExample {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(5);

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                buffer.put(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Producer");

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                buffer.getNext();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Consumer");

        producer.start();
        consumer.start();
    }
}


class Buffer {
    private final LinkedList<Integer> bufferList = new LinkedList<>();

    private final Lock lock = new ReentrantLock();
    private final Condition putCondition = lock.newCondition();
    private final Condition getCondition = lock.newCondition();

    private int capacity;

    public Buffer(int capacity) {
        this.capacity = capacity;
    }

    public void put(int value) {
        lock.lock();
        try {
            while (bufferList.size() == capacity) {
                System.out.println(Thread.currentThread().getName() + " is waiting to put " + value + " in buffer.");
                putCondition.await();
            }
            bufferList.add(value);
            System.out.println(Thread.currentThread().getName() + " put " + value + " in buffer. Buffer: " + bufferList);
            getCondition.signal();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public int getNext() {
        lock.lock();
        try {
            while (bufferList.isEmpty()) {
                System.out.println(Thread.currentThread().getName() + " is waiting to get next value.");
                getCondition.await();
            }
            int value = bufferList.poll();
            System.out.println(Thread.currentThread().getName() + " got " + value + ". Buffer: " + bufferList);
            putCondition.signal();
            return value;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

    }
}
