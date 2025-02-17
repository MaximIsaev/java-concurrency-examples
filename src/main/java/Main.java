public class Main {
    public static void main(String[] args) {
//        Thread consoleThread = new Thread(() -> System.out.println("This is my new Thread"));
//        consoleThread.start();
        System.out.println("This is my Main thread");

//        for (int i = 0; i < 100; i++) {
//            MyCustomThread customThread = new MyCustomThread(i);
//            customThread.start();
//        }

        for (int i = 0; i < 100; i++) {
            new Thread(new MyRunnable()).start();
        }

    }
}

class MyRunnable implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            System.out.println("This is MyRunnable, Thread ID=" + Thread.currentThread().getId());
        } catch (Exception e) {
            System.out.println("Error occurred " + e.getMessage());
        }
    }
}

class MyCustomThread extends Thread {
    private final int myId;

    public MyCustomThread(int id) {
        this.myId = id;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            System.out.println("This is MyCustomThread id=" + myId + ", Thread ID=" + Thread.currentThread().getId());
        } catch (Exception e) {
            System.out.println("Error occurred " + e.getMessage());
        }
    }
}
