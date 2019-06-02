package softeng751.g21.executors.sleepy;

import softeng751.g21.Measurements;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws Exception {
        SleepyExecutorService service = new SleepyExecutorService();

        for (int i = 0; i < 8; i++) {
            service.submit(() -> {
                for (int j = 0; j < 1000; j++) {
                    // Takes roughly 5ms
                    Measurements.work(50_000);
                    // Sleep for another 5ms to utilize half of the thread
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        service.awaitTermination(1, TimeUnit.HOURS);
    }

}
