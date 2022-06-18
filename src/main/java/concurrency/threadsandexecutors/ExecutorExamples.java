package concurrency.threadsandexecutors;

import java.util.concurrent.*;

public class ExecutorExamples {

    public static void main(String[] args) {
        ExecutorService executor = java.util.concurrent.Executors.newSingleThreadExecutor();

        executor.submit(task("Santi", 60));

        // The executor will keep listening for new tasks, so it needs to be terminated
        // shutdown() will wait fot the last task to be finished.
        // shutdownNow() interrupts the current last running task to shut down the executor.
        // Every non terminated task will throe InterruptedException when the executor is down.
        try{
            // Softly shutdown by waiting 10 seconds to some task to finish
            System.out.println("Attempt to shutdown executor.");
            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException ie) {
            System.out.println("Task interrupted.");
            System.out.println(ie.getMessage());
        } finally {
            // After 10 seconds it will interrupt the current running task.
            if(!executor.isTerminated()){
                System.out.println("Cancel non-finished tasks.");
            }
            executor.shutdownNow();
            System.out.println("Shutdown finished.");
        }
    }

    // This method simulate a task, process or a job that takes some time.
    public static Runnable task(String name, long sleepSeconds) {
        return () -> {
            try {
                TimeUnit.SECONDS.sleep(sleepSeconds);
                System.out.println("Printing " + name + " after " + sleepSeconds + "s.");
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        };
    }
}
