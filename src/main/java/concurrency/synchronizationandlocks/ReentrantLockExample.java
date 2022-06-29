package concurrency.synchronizationandlocks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import static concurrency.utils.ConcurrentUtils.sleep;
import static concurrency.utils.ConcurrentUtils.stop;

public class ReentrantLockExample {

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
    }
}
