package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorExample {
    public static void main(String[] args) {
        Store store = new CachedStore();
        int orders = 100;
        for (int i = 1; i <= orders; i++) {
            try {
                boolean lastOrder = i == orders;
                System.out.println("Заказ " + i + " поступил в систему");
                store.processOrder((long) (Math.random() * 4000), i, lastOrder);
                Thread.sleep((long) (Math.random() * 150));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}

interface Store {
    void processOrder(long timeToProcess, int orderNumber, boolean lastOrder);
}


class FixedStore implements Store {
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void processOrder(long timeToProcess, int orderNumber, boolean lastOrder) {
        executorService.submit(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " оббработает заказ. Обработка заказа " + orderNumber + " займет " + timeToProcess + " мс.");
                Thread.sleep(timeToProcess);
                System.out.println("Заказ " + orderNumber + " обработан успешно");
                return 200;
            } catch (InterruptedException e) {
                System.out.println("Ошибка обработки заказа " + orderNumber + ": " + e.getMessage());
                return 500;
            }
        });

        if (lastOrder) {
            System.out.println("Последний заказ. Выключение обработчика.");
            executorService.shutdown();
        }

        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            throw new RuntimeException(e);
        }
    }
}

class CachedStore implements Store {
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public void processOrder(long timeToProcess, int orderNumber, boolean lastOrder) {
        executorService.submit(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " оббработает заказ. Обработка заказа " + orderNumber + " займет " + timeToProcess + " мс.");
                Thread.sleep(timeToProcess);
                System.out.println("Заказ " + orderNumber + " обработан успешно");
                return 200;
            } catch (InterruptedException e) {
                System.out.println("Ошибка обработки заказа " + orderNumber + ": " + e.getMessage());
                return 500;
            }
        });

        if (lastOrder) {
            try {
                System.out.println("Последний заказ. Выключение обработчика.");
                executorService.shutdown();
                if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                    System.out.println("Завершение принудительное");
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                System.out.println("Ошибка при завершении " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }
}

class FixedThreadPoolExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 1; i <= 4; i++) {
            int taskId = i;
            executor.execute(() -> {
                try {
                    System.out.println("Задача " + taskId + " выполняется в " + Thread.currentThread().getName());
                    Thread.sleep(taskId == 1 ? 5000 : 1000); // Первая задача выполняется 5 сек, остальные 1 сек
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
    }
}

class WorkStealingPoolExample {
    public static void main(String[] args) {
        // Используем новый пул потоков для кражи задач
        ExecutorService executor = Executors.newWorkStealingPool();

        // Запускаем несколько задач
        for (int i = 1; i <= 6; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("Задача " + taskId + " выполняется потоком " + Thread.currentThread().getName());
                try {
                    if (taskId % 2 == 0) {
                        Thread.sleep(3000); // Чётные задачи - долгие
                    } else {
                        Thread.sleep(1000); // Нечётные - быстрые
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Задача " + taskId + " завершена потоком " + Thread.currentThread().getName());
            });
        }

        // Даем время задачам выполниться
        try {
            Thread.sleep(10000); // Ждем 10 секунд, чтобы все задачи успели завершиться
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();  // Завершаем работу пула потоков
    }
}


