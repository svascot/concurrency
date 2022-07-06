package concurrency.synchronizationandlocks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

import static concurrency.utils.ConcurrentUtils.sleep;
import static concurrency.utils.ConcurrentUtils.stop;

public class StampedLockExample {

    public static void main(String[] args) {
        stampedLockExample();
        tryOptimisticReadExample();
    }

    // this works as ReadWriteLock but returns a stamp to unlock it later.
    private static void stampedLockExample() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Map<String, String> map = new HashMap<>();
        StampedLock lock = new StampedLock();

        // This will hold the thread for 2 seconds before write,
        // so any other thread that wants to read will be blocked
        executor.submit(() -> {
            // This locks returns a stamp that is used in the finally to unlock it.
            long stamp = lock.writeLock();
            try {
                sleep(2);
                map.put("foo", "bar");
            } finally {
                // the stamp returned by the lock is used here to unlock it.
                lock.unlockWrite(stamp);
            }
        });

        // This will read and hold the thread for 2 seconds
        // so any other read-task without the lock will wait.
        Runnable readTask = () -> {
            long stamp = lock.readLock();
            try {
                System.out.println(map.get("foo"));
                sleep(2);
            } finally {
                lock.unlockRead(stamp);
            }
        };

        executor.submit(readTask);
        executor.submit(readTask);

        stop(executor);
    }

    private static void tryOptimisticReadExample() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        StampedLock lock = new StampedLock();

        executor.submit(() -> {
            // The lock is valid right after acquiring the lock, then will be valid until another
            // thread acquire the write-lock and will be invalid even if the write-lock is released.
            long stamp = lock.tryOptimisticRead();
            try {
                System.out.println("Optimistic lock valid: " + lock.validate(stamp));
                sleep(1);
                System.out.println("Optimistic lock valid: " + lock.validate(stamp));
                sleep(2);
                System.out.println("Optimistic lock valid: " + lock.validate(stamp));
            } finally {
                lock.unlock(stamp);
            }
        });

        // This will lock to write.
        executor.submit(() -> {
            long stamp = lock.writeLock();
            try {
                System.out.println("Write lock acquired");
                sleep(2);
            } finally {
                lock.unlock(stamp);
                System.out.println("Write done");
            }
        });

        stop(executor);
    }
}
