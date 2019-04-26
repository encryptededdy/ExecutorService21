package softeng751.g21;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BenchmarkExecutorServiceFactory {
    private static final int WORK_DIVISOR = 50;
    private static final int BASELINE_AVERAGES = 4;
    private static final double PERFORMANCE_CUTOFF = 1.1;

    public static void main(String[] args) {
        runBenchmarkAndGetExecutorService();
    }

    @SuppressWarnings("WeakerAccess")
    public static ExecutorService runBenchmarkAndGetExecutorService() {
        int hwThreads = Runtime.getRuntime().availableProcessors();
        double baseline = IntStream.range(0, BASELINE_AVERAGES).mapToLong(i -> testWithThreads(hwThreads)).average().orElse(0);
        System.out.println("Average with availableProcessors: "+baseline+"ms");
        int maxOptimalThreads = 0;
        for (int i = hwThreads; i <= hwThreads * 512; i*=2) {
            // Search for max threads before things start getting slow
            double runTime = testWithThreads(i);
            if (runTime < baseline * PERFORMANCE_CUTOFF) {
                maxOptimalThreads = i;
            } else {
                break;
            }
        }
        System.out.println("Max optimal threads before slowdown is "+maxOptimalThreads);
        return Executors.newFixedThreadPool(maxOptimalThreads);
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
