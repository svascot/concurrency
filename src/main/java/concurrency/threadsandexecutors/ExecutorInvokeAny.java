package concurrency.threadsandexecutors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class ExecutorInvokeAny {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int amountOfTasks = new Random().nextInt(10);

        // Create a list of callables that takes a random time to finish.
        List<Callable<String>> callables = new ArrayList<>();
        for (int i =0; i < amountOfTasks; i++) {
            callables.add(task());
        }

        // Create a thread pool with the same amount of tasks, IDK if this is not the best way to optimize the pool.
        ExecutorService executor = Executors.newFixedThreadPool(amountOfTasks);

        // Invokes all the callable and will block and wait until one finish and return it value.
        String result = executor.invokeAny(callables);
        System.out.println(result);
        executor.shutdown();
    }

    static Callable<String> task () {
        return () -> {
            int sleepSeconds = new Random().nextInt(10);
            TimeUnit.SECONDS.sleep(sleepSeconds);
            return "Process takes: " + sleepSeconds + " seconds to finish.";
        };
    }
}
