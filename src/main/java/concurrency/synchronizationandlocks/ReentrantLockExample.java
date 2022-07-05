package concurrency.synchronizationandlocks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import static concurrency.utils.ConcurrentUtils.sleep;
import static concurrency.utils.ConcurrentUtils.stop;

public class ReentrantLockExample {
    static int count = 0;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        ReentrantLock lock = new ReentrantLock();

        // This will lock the thread for 2 seconds.
        executor.submit(()-> {
            lock.lock();
            try{
                sleep(2);
            } finally {
                lock.unlock();
            }
        });

        // Here it will check the status of that ReentryLock.
        executor.submit(()->{
            System.out.println("Locked: " + lock.isLocked());
            System.out.println("Held by me: " + lock.isHeldByCurrentThread());
            boolean locked = lock.tryLock();
            System.out.println("Lock acquired: " + locked);
        });

        stop(executor);

        testIncrementCount();
    }

    // Example working with locks to prevent a race condition working over a counter.
    private static void testIncrementCount(){
        ExecutorService executor = Executors.newFixedThreadPool(2);
        ReentrantLock lock = new ReentrantLock();

        // In this example, the counter should be increased by 10.000
        // With the lock implementation it avoids the race condition, otherwise
        // the counter will not reach the expected value.
        IntStream.range(0,10000).forEach(i-> executor.submit(()-> {
            lock.lock();
            try{
                count++;
            } finally {
                lock.unlock();
            }
        }));

        stop(executor);
        System.out.println("Counter: " + count);
    }
}
