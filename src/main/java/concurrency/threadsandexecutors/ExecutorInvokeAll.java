package concurrency.threadsandexecutors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class ExecutorInvokeAll {
    public static void main(String[] args) throws InterruptedException {
        int amountOfTasks = new Random().nextInt(10);

        // Create a list of callables that takes a random time to finish.
        List<Callable<String>> callables = new ArrayList<>();
        for (int i =0; i < amountOfTasks; i++) {
            callables.add(task());
        }

        // Create a thread pool with the same amount of tasks, IDK if this is not the best way to optimize the pool.
        ExecutorService executor = Executors.newFixedThreadPool(amountOfTasks);

        // Invokes all the callables in the list to execute them at the same time.
        executor.invokeAll(callables).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .forEach(System.out::println);

        executor.shutdown();
        System.out.println(amountOfTasks + " tasks.");
    }

    static Callable<String> task () {
        return () -> {
            int sleepSeconds = new Random().nextInt(10);
            TimeUnit.SECONDS.sleep(sleepSeconds);
            return "Process takes: " + sleepSeconds + " seconds to finish.";
        };
    }
}
