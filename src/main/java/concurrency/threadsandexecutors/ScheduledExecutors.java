package concurrency.threadsandexecutors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutors {

    public static void main(String[] args) throws InterruptedException {
        scheduledExecutorExample();
        scheduleAtFixedRateExecutorExample();
        scheduleWithFixedDelayExecutorExample();
    }

    private static void scheduleWithFixedDelayExecutorExample() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        // This task will take 4 seconds to be process.
        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(4);
                System.out.println("Fixed Delay task. " + System.nanoTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        int initialDelay = 0;
        int period = 2;
        // This will run the task 2 seconds after the previous one was finished.
        // if the task takes 4s to finish and the thread waits 2s to run the task again
        // the behavior will be 0s, 6s, 12s, 18s...
        executor.scheduleWithFixedDelay(task, initialDelay, period, TimeUnit.SECONDS);
    }

    private static void scheduleAtFixedRateExecutorExample() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> System.out.println("Fixed rate task. " + System.nanoTime());

        int initialDelay = 4;
        int period = 2;
        // This executor will start after 4 seconds and will run the task every 2 second
        // if the task takes more time to complete that the period time, the thread pool
        // will work to capacity.
        executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
    }

    private static void scheduledExecutorExample() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());
        // Schedule a task to run after 10 seconds.
        ScheduledFuture<?> future = executor.schedule(task, 10, TimeUnit.SECONDS);

        // After 4 seconds ask for the remaining delay to execute the task.
        TimeUnit.MILLISECONDS.sleep(4000);
        long remainingDelay = future.getDelay(TimeUnit.MILLISECONDS);
        System.out.println("Remaining Delay: " + remainingDelay + "ms.");

        executor.shutdown();
    }


}
