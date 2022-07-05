package concurrency.synchronizationandlocks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static concurrency.utils.ConcurrentUtils.sleep;
import static concurrency.utils.ConcurrentUtils.stop;

public class ReadWriteLockExample {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Map<String, String> map = new HashMap<>();
        ReadWriteLock lock = new ReentrantReadWriteLock();

        System.out.println("Start");

        // This will hold the thread for 2 seconds before write,
        // so any other thread that wants to read will be blocked
        executor.submit(() -> {
            lock.writeLock().lock();
            try {
                sleep(2);
                map.put("foo", "bar");
            } finally {
                lock.writeLock().unlock();
            }
        });

        // This will read and hold the thread for 2 seconds
        // so any other read-task without the lock will wait.
        Runnable readTask = () -> {
            lock.readLock().lock();
            try {
                System.out.println(map.get("foo"));
                sleep(2);
            } finally {
                lock.readLock().unlock();
            }
        };

        // Both read-tasks are able to read at the same time.
        executor.submit(readTask);
        executor.submit(readTask);

        stop(executor);
    }
}
