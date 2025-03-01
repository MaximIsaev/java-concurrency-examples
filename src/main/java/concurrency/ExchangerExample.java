package concurrency;

import java.util.Arrays;
import java.util.concurrent.Exchanger;

public class ExchangerExample {
    public static void main(String[] args) {
        Exchanger<long[]> exchanger = new Exchanger<>();

        Runnable creator = () -> {
            long[] arr = new long[10];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (i * 7) / 3 - 1 + 15;
            }
            System.out.println(Thread.currentThread().getName() + " created new array " + Arrays.toString(arr));
            try {
                System.out.println(Thread.currentThread().getName() + " sent the array to sqrt");
                exchanger.exchange(arr);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        };

        Runnable power = () -> {
            try {
                long[] arr = exchanger.exchange(null);
                System.out.println(Thread.currentThread().getName() + " got the array " + Arrays.toString(arr));

                long[] powArr = new long[arr.length];
                for (int i = 0; i < powArr.length; i++) {
                    powArr[i] = (long) Math.pow(arr[i], 2);
                }
                System.out.println(Thread.currentThread().getName() + " created new array of power of 2 " + Arrays.toString(powArr));
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        };

        new Thread(creator, "Creator").start();
        new Thread(power, "Power 2").start();

    }
}


