package softeng751.g21.executors.sleepy;

import softeng751.g21.Measurements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws Exception {
        ExecutorService service;

        int threads = 4;

        if (args.length == 0 || args[0].equals("sleepy")) {
            service = new SleepyExecutorService(threads);
        } else if (args[0].equals("fixed")) {
            service = Executors.newFixedThreadPool(threads);
        } else {
            throw new UnsupportedOperationException("Unknown executor service " + args[0]);
        }

        List<Future> futures = new ArrayList<>();

        long start = System.currentTimeMillis();

        for (int i = 0; i < 32; i++) {
            // Submit a task that sleeps for a significant proportion of the task
            Future<?> future = service.submit(() -> {
                for (int j = 0; j < 50; j++) {
                    Measurements.work(500_000, true);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            futures.add(future);
        }

        // Wait for all tasks to finish
        for (Future future : futures) {
            future.get();
        }

        long end = System.currentTimeMillis();
        System.out.println("Took " + (end - start) + "ms to finish");

        System.exit(0);
    }

}
