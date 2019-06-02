package softeng751.g21.executors.sleepy;

import softeng751.g21.Measurements;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws Exception {
        SleepyExecutorService service = new SleepyExecutorService();

        service.submit(() -> {
            System.out.println("Start");
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Done sleeping");
            Measurements.work();
            System.out.println("Completed");
        });

        service.awaitTermination(1, TimeUnit.HOURS);
    }

}
