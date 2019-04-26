package softeng751.g21;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.IntStream;

public class BenchmarkExecutorServiceFactory {
    private static final int WORK_DIVISOR = 50;

    public static void main(String[] args) {
        runBenchmarkAndGetExecutorService();
    }

    @SuppressWarnings("WeakerAccess")
    public static void runBenchmarkAndGetExecutorService() {
        int hwThreads = Runtime.getRuntime().availableProcessors();
        for (int i = 1; i <= hwThreads * 512; i*=2) {
            System.out.println("Tests with "+i+" threads took "+testWithThreads(i)+"ms");
        }
    }

    private static long testWithThreads(int numOfThreads) {
        Thread[] threads = IntStream.range(0, numOfThreads).mapToObj(i -> new Thread(() -> {
            doWork(numOfThreads);
        })).toArray(Thread[]::new);
        Instant start = Instant.now();
        Arrays.stream(threads).forEach((Thread::start));
        Arrays.stream(threads).forEach((thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                // We're dead, jim
            }
        }));
        Instant end = Instant.now();
        return Duration.between(start, end).toMillis();
    }

    private static void doWork(int fraction) {
        for (int i = 0; i < Integer.MAX_VALUE/(fraction*WORK_DIVISOR); i++) {
            double x = 4.0;
            double memes = x * Math.PI / -Math.acos(Math.PI) * Math.scalb(2, 5);
        }
    }
}
