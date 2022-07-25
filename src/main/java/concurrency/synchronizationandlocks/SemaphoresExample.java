package concurrency.synchronizationandlocks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static concurrency.utils.ConcurrentUtils.sleep;
import static concurrency.utils.ConcurrentUtils.stop;

public class SemaphoresExample {

    // Semaphores are useful to limit the amount concurrent access to certain part of the code.
    // It's important to use try/finally to release the semaphore in case of exceptions.
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Semaphore semaphore = new Semaphore(5);

        Runnable task = () -> {
            boolean permit = false;
            try {
                permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
                if (permit) {
                    System.out.println("Semaphore acquired.");
                    sleep(5);
                } else {
                    System.out.println("Could not acquire semaphore.");
                }
            } catch (InterruptedException interruptedException) {
                System.out.println(interruptedException.getMessage());
            } finally {
                if (permit) {
                    semaphore.release();
                }
            }
        };

        IntStream.range(0, 10).forEach(i -> executor.submit(task));

        stop(executor);
    }
}
