package threads.latch;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class CountDownLatchBeginSync {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch beginLatch = new CountDownLatch(6);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch workLatch = new CountDownLatch(6);

        List<RacerThread> racers = Stream.generate(() -> new RacerThread(beginLatch, startLatch, workLatch, false))
                .limit(5)
                .toList();

        racers.forEach(RacerThread::start);
        new RacerThread(beginLatch, startLatch, workLatch, true).start();

        beginLatch.await();

        System.out.println("The race starts!!!");
        startLatch.countDown();

        workLatch.await(10, TimeUnit.SECONDS);

        System.out.println("The race is finished!!!");
    }

}

class RacerThread extends Thread {
    private CountDownLatch beginLatch;
    private CountDownLatch startLatch;
    private CountDownLatch workLatch;
    private boolean broken;

    public RacerThread(CountDownLatch beginLatch, CountDownLatch startLatch, CountDownLatch workLatch, boolean broken) {
        this.beginLatch = beginLatch;
        this.startLatch = startLatch;
        this.workLatch = workLatch;
        this.broken = broken;
    }

    @Override
    public void run() {
        System.out.println("Racer " + Thread.currentThread().getId() + " is waiting");
        beginLatch.countDown();
        try {
            startLatch.await();
            if (broken) throw new RuntimeException("Racer " + Thread.currentThread().getId() + " is broken");
            System.out.println("Racer " + Thread.currentThread().getId() + " is working");
            workLatch.countDown();
        } catch (InterruptedException e) {
            e.getMessage();
        } finally {
        }
    }
}
