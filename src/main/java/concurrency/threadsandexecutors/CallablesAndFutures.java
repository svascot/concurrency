package concurrency.threadsandexecutors;

import java.util.concurrent.*;

public class CallablesAndFutures {
    public static void main(String[] args) {
        // Thread pool of one.
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> futureInt = executor.submit(task(5));

        System.out.println("Is future done? " + futureInt.isDone());
        executor.shutdown();

        try {
            // Any call to future.get() will block and wait until the callable has been terminated.
            // To prevent that it runs forever we can throw a timeout after wait a max amount on time.
            Integer futureResult = futureInt.get(10, TimeUnit.SECONDS);

            System.out.println("Is future done? " + futureInt.isDone());
            System.out.println("Result: " + futureResult);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    // Callables are like Runnables but instead of been void they return a value.
    static Callable<Integer> task (long sleepSeconds) {
        return () -> {
            TimeUnit.SECONDS.sleep(sleepSeconds);
            return 0;
        };
    }
}
