package softeng751.g21.movingAverageExecutorService;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;

public class WatcherThread implements Runnable, Callable<Void> {
    private static final int POLL_DELAY_MS = 100;
    private static final double SMOOTHING_FACTOR = 0.5;
    private static final int MAX_THREADS_FACTOR = 1;

    private MovingAverageAdaptiveExecutorService service;

    public WatcherThread(MovingAverageAdaptiveExecutorService service) {
        this.service = service;
    }

    @Override
    public void run() {
        // TODO: Handle shutdown
        System.out.println("Watcher thread started");
        Double lastEMA = null;
        for (;;) {
            int activeThreads = service.getActiveCount();
            if (lastEMA != null) {
                double ema = (SMOOTHING_FACTOR * activeThreads) + (1 - SMOOTHING_FACTOR) * lastEMA;
                // Modified EMA is weighted based on difference from the last one
                double modifiedEma = (ema > lastEMA) ? activeThreads + (activeThreads - lastEMA) : ema;
                // allocate threads as needed
                updatePoolSize((int) Math.ceil(modifiedEma));
                lastEMA = modifiedEma;
            } else {
                updatePoolSize(activeThreads);
                lastEMA = (double) activeThreads;
            }
            try {
                Thread.sleep(POLL_DELAY_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updatePoolSize(int size) {
        System.out.println("Pool Size: " + size);
        // If max pool size drops below double the core pool size, update it
        if (service.getMaximumPoolSize() < size * MAX_THREADS_FACTOR)
            service.setMaximumPoolSize(size * MAX_THREADS_FACTOR);
        service.setCorePoolSize(size);
    }

    @Override
    public Void call() throws Exception {
        run();
        return null;
    }
}
