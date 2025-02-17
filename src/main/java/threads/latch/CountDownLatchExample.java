package threads.latch;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        int limit = 5;
        CountDownLatch latch = new CountDownLatch(limit);

        List<MyThread> allThreads = Stream.generate(() -> new MyThread(latch))
                .limit(5)
                .toList();

        allThreads.forEach(MyThread::start);

        latch.await();

        System.out.println("All workers done!");

    }
}

class MyThread extends Thread {
    private CountDownLatch latch;

    public MyThread(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " works done!");
        latch.countDown();
    }
}
