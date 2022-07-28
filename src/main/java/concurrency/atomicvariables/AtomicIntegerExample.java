package concurrency.atomicvariables;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static concurrency.utils.ConcurrentUtils.*;

public class AtomicIntegerExample {

    // Atomic operations are thread-safe without using synchronized keyword or locks,
    // Using the compare-and-swap atomic instruction (CAS) that is supported by most of the modern CPUs
    // That's why atomic operations can be faster that using synchronized locks.
    public static void main(String[] args) {
        atomicIntExample();
        lambdaExample();
        accumulateAndGetExample();
    }

    private static void atomicIntExample() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        AtomicInteger atomicInt = new AtomicInteger(0);

        IntStream.range(0,1000).forEach(i -> executor.submit(atomicInt::incrementAndGet));

        stop(executor);

        System.out.println(atomicInt.get());
    }
    private static void lambdaExample() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        AtomicInteger atomicInt = new AtomicInteger(0);

        // updateAndGet accepts lambda expressions to perform arithmetic operations.
        IntStream.range(0,1000).forEach(i -> {
            Runnable task = () -> atomicInt.updateAndGet(operand -> operand + 2);
            executor.submit(task);
        });

        stop(executor);

        System.out.println(atomicInt.get());
    }

    private static void accumulateAndGetExample() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        AtomicInteger atomicInt = new AtomicInteger(0);

        // The method accumulateAndGet accepts a IntBinaryOperator lambda,
        // This example is used to sum all the values from 0 to 1000 concurrently
        IntStream.range(0, 1000).forEach(i -> {
            Runnable task = ()-> atomicInt.accumulateAndGet(i, (n,m) -> n + m);
            executor.submit(task);
        });

        stop(executor);

        System.out.println(atomicInt.get());
    }
}
