package concurrency;

import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);

        Runnable runnable = () -> {
            String humanName = Thread.currentThread().getName();
            System.out.println(humanName + " is waiting for seat");
            try {
                semaphore.acquire();
                System.out.println(humanName + " got seat for 3 seconds");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println(humanName + " has released the place");
                semaphore.release();
            }
        };

        for (int i = 0; i < 5; i++) {
            new Thread(runnable, "Человек " + i).start();
        }

    }
}
