package concurrency;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    public static void main(String[] args) {
        int loaders = 3;
        CountDownLatch latch = new CountDownLatch(loaders);

        Thread rest = new Thread(new Loader("REST", 10000, latch));
        Thread db = new Thread(new Loader("DB", 15000, latch));
        Thread file = new Thread(new Loader("File", 3000, latch));

        try {
            System.out.println("Ждем завершения загрузки данных.");
            rest.start();
            db.start();
            file.start();

            latch.await();
            System.out.println("Все загрузки завершились.");

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Loader implements Runnable {
    private final String name;
    private final long time;
    private final CountDownLatch latch;

    public Loader(String name, long time, CountDownLatch latch) {
        this.name = name;
        this.time = time;
        this.latch = latch;
    }

    private void load() {
        try {
            System.out.println("[" + name + "]: загрузка данных " + time + "мс ...");
            Thread.sleep(time);
            System.out.println("[" + name + "]: загрузка завершена.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            latch.countDown();
        }
    }

    @Override
    public void run() {
        load();
    }
}
