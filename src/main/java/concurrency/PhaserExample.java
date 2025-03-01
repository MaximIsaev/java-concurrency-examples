package concurrency;

import java.util.concurrent.Phaser;

public class PhaserExample {
    public static void main(String[] args) {
        Phaser phaser = new Phaser(1) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("=== Фаза " + phase + " завершена. ===");
                return super.onAdvance(phase, registeredParties);
            }
        };

        new Thread(new Task(phaser, "T1", 5)).start();
        new Thread(new Task(phaser, "T2", 10)).start();
        new Thread(new Task(phaser, "T3", 20)).start();

        while (phaser.getRegisteredParties() > 1) {
            phaser.arriveAndAwaitAdvance();
        }

        phaser.arriveAndDeregister();
        System.out.println("Главный поток завершил Phaser");
    }
}

class Task implements Runnable {
    private final Phaser phaser;
    private final String name;
    private final int maxPhases;

    public Task(Phaser phaser, String name, int maxPhases) {
        this.phaser = phaser;
        this.name = name;
        this.maxPhases = maxPhases;
        phaser.register();
    }

    @Override
    public void run() {
        for (int i = 0; i < maxPhases; i++) {
            System.out.println(name + " выполняет фазу " + phaser.getPhase());
            phaser.arriveAndAwaitAdvance();
        }
        System.out.println(name + " закончил свои фазы ===");
        phaser.arriveAndDeregister();
    }
}

