package concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

    }

    public static Callable<String> task (String name, long sleepSeconds) {
        return () -> {
            TimeUnit.SECONDS.sleep(sleepSeconds);
            return name;
        };
    }
}
