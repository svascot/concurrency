package concurrency.atomicvariables;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

public class LongAdderAndLongAccumulator {
    public static void main(String[] args) {
        longAdderExmaple();
    }

    private static void longAdderExmaple() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        LongAdder longAdder = new LongAdder();

        // TODO Explain
        IntStream.range(0, 100).forEach(i -> executor.submit(longAdder::increment));
    }
}
