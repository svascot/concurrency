package concurrency.threadsandexecutors;

public class ThreadExample {

    public static void main(String[] args) {

        Runnable printThreadName = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Thread name: " + threadName);
        };

        // This will run in the main thread
        printThreadName.run();

        // Create a new thread with a runnable.
        Thread thread = new Thread(printThreadName);
        thread.start();

        System.out.println("Done!");
    }
}
