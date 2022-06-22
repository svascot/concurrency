package concurrency.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ConcurrentUtils {

    public static void stop(ExecutorService executor) {
        try {
            executor.shutdown();
            executor.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException ie) {
            System.out.println("Termination interrupted. "+ ie);
        } finally {
            if (!executor.isTerminated()){
                System.out.println("Killing non-terminated tasks");
            }
            executor.shutdownNow();
        }
    }

    public static void sleep(int secondsToSleep) {
        try {
            TimeUnit.SECONDS.sleep(secondsToSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
