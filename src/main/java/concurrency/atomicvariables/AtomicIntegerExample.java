package concurrency.atomicvariables;

import concurrency.utils.ConcurrentUtils;

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
        ExecutorService executor = Executors.newFixedThreadPool(2);
        AtomicInteger atomicInt = new AtomicInteger(0);

        IntStream.range(0,1000).forEach(i -> executor.submit(atomicInt::incrementAndGet));

        stop(executor);

        System.out.println(atomicInt.get());
    }
}
