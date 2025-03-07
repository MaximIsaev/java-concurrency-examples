package concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CyclicBarrierExample {
    public static void main(String[] args) {
        int threadsNumber = 4;
        CyclicBarrier barrier = new CyclicBarrier(threadsNumber, () -> {
            System.out.println("Все потоки достигли барьера");
        });

        Runnable runnable = () -> {
            String name = Thread.currentThread().getName();
            try {
                long millis = (long) (Math.random() * 15000);
                System.out.println(name + " выполняет работу " + millis + " мс");
                Thread.sleep(millis);
                System.out.println(name + " ждёт.");
                if(!barrier.isBroken()) {
                    barrier.await(300, TimeUnit.MILLISECONDS);
                    System.out.println(name + " продолжает выполнять работу.");
                } else {
                    System.out.println(name + " продолжает выполнять работу, т.к. барьер сломан.");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            } catch (TimeoutException e) {
                System.out.println(name + " продолжает выполнять работу без ожидания.");
            }
        };

        for (int i = 0; i < threadsNumber; i++) {
            new Thread(runnable, "Поток " + i).start();
        }
    }
}
