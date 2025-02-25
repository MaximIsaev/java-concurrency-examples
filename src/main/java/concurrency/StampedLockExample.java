package concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

public class StampedLockExample {
    public static void main(String[] args) {
        Cache<String, String> cache = new Cache<>();

        Thread writer1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                cache.put("Ключ " + i, "Значение " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Writer 1");

        Thread reader1 = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                System.out.println(Thread.currentThread().getName() + " got value " + cache.get("Ключ " + i));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Reader 1");

        Thread sizer = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " got size " + cache.size());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Sizer");

        Thread remover = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                cache.remove("Ключ " + i);
                System.out.println(Thread.currentThread().getName() + " removed element");
                try {
                    Thread.sleep(350);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Remover");

        reader1.start();
        writer1.start();
        sizer.start();
        remover.start();

    }
}


class Cache<K, V> {
    private final StampedLock lock;

    private final Map<K, V> map;

    public Cache() {
        this.map = new HashMap<>();
        this.lock = new StampedLock();
    }

    public void put(K key, V value) {
        long stamp = lock.writeLock();

        try {
            System.out.println(Thread.currentThread().getName() + " writes item key=" + key + ", value=" + value);
            map.put(key, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public V get(K key) {
        long stamp = lock.tryOptimisticRead();

        System.out.println(Thread.currentThread().getName() + " try to get read optimistic lock");

        V value = map.get(key);

        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            System.out.println(Thread.currentThread().getName() + " got exclusive read lock");
            try {
                value = map.get(key);
                return value;
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlockRead(stamp);
            }
        }

        return value;
    }

    public void remove(K key) {
        long stamp = lock.writeLock();
        try {
            map.remove(key);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public int size() {
        long stamp = lock.tryOptimisticRead();

        int size = map.size();

        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                size = map.size();
                return size;
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlockRead(stamp);
            }
        }

        return size;
    }


}
