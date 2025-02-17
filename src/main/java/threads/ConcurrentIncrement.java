package threads;

public class ConcurrentIncrement {
    public static void main(String[] args) {
        Counter counter = new Counter();

        int limit = 1000;

        Thread counter1 = new Thread(() -> {
            while (counter.getCounter() < limit) {
                counter.increment();
            }
        });

        Thread counter2 = new Thread(() -> {
            while (counter.getCounter() < limit) {
                counter.increment();
            }
        });

        Thread counter3 = new Thread(() -> {
            while (counter.getCounter() < limit) {
                counter.increment();
            }
        });

        Thread counter4 = new Thread(() -> {
            while (counter.getCounter() < limit) {
                counter.increment();
            }
        });

        counter1.start();
        counter2.start();
        counter3.start();
        counter4.start();

        try {
            counter1.join();
            counter2.join();
            counter3.join();
            counter4.join();

            System.out.println("Number=" + counter.getCounter());
        } catch (Exception e) {

        }
    }
}

class Counter {
    private int number = 0;

    int getCounter() {
        return number;
    }

    void increment() {
        synchronized (this) {
            number++;
        }
        System.out.println("Thread " + Thread.currentThread().getName() + " number=" + number);
    }
}
