package concurrency.synchronizationandlocks;

import concurrency.utils.ConcurrentUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class SynchronizedExamples {

    private static int count = 0;

    public static void main(String[] args) {
        testNonSyncIncrementCount();
        testSyncIncrementCount();
    }

    // In this case we will have a different output every time we run the code since there are two threads accessing
    // the same variable and trying to execute an operation (read value, increment, store the new value).
    // This is a Race Condition
    private static void testNonSyncIncrementCount() {
        count = 0;
        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 10000)
                .forEach(i-> executor.submit(SynchronizedExamples::incrementCount));
        ConcurrentUtils.stop(executor);

        System.out.println("Non synchronized increment: " + count);
    }

    // In this case we will avoid the race condition using a synchronized method to perform the operation
    // (read value, increment, store the new value).
    private static void testSyncIncrementCount(){
        count = 0;
        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 10000)
                .forEach(i-> executor.submit(SynchronizedExamples::syncIncrementCount));
        ConcurrentUtils.stop(executor);

        System.out.println("Synchronized increment: " + count);
    }

    private static synchronized void syncIncrementCount() {
        count = count + 1;
    }

    private static void incrementCount() {
        count = count + 1;
    }
}
