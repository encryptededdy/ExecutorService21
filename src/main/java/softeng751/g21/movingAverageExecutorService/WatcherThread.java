package softeng751.g21.movingAverageExecutorService;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;

public class WatcherThread implements Runnable {
    private static final int POLL_DELAY_MS = 250;
    private static final double SMOOTHING_FACTOR = 0.5;
    private static final int MAX_THREADS_FACTOR = 1;
    private static final boolean ENABLE_LOGGING = true;

    private boolean terminated = false;

    // Logging
    private ArrayList<Integer> activeThreadsLog = new ArrayList<>();
    private ArrayList<Double> emaLog = new ArrayList<>();
    private ArrayList<Long> completedTasksLog = new ArrayList<>();
    private ArrayList<Long> taskCountLog = new ArrayList<>();

    private MovingAverageAdaptiveExecutorService service;

    public WatcherThread(MovingAverageAdaptiveExecutorService service) {
        this.service = service;
    }

    public void requestTermination() {
        terminated = true;
    }

    @Override
    public void run() {
        System.out.println("Watcher thread started");
        Double lastEMA = null;
        for (;;) {
            // Check if we've been asked to stop...
            if (terminated) {
                // write out CSV
                return;
            }

            int activeThreads = service.getActiveCount();
            if (lastEMA != null) {
                double ema = (SMOOTHING_FACTOR * activeThreads) + (1 - SMOOTHING_FACTOR) * lastEMA;
                // Modified EMA is weighted based on difference from the last one
                double modifiedEma = (ema > lastEMA) ? activeThreads + (activeThreads - lastEMA) : ema;
                // allocate threads as needed
                updatePoolSize((int) Math.ceil(modifiedEma));

                if (ENABLE_LOGGING) {
                    activeThreadsLog.add(activeThreads);
                    emaLog.add(modifiedEma);
                }

                lastEMA = ema; // We don't store the modified EMA.
            } else {
                updatePoolSize(activeThreads);
                lastEMA = (double) activeThreads;

                if (ENABLE_LOGGING) {
                    activeThreadsLog.add(activeThreads);
                    emaLog.add((double) activeThreads);
                }
            }
            if (ENABLE_LOGGING) {
                completedTasksLog.add(service.getCompletedTaskCount());
                taskCountLog.add(service.getTaskCount());
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

    public ArrayList<Integer> getActiveThreadsLog() {
        return activeThreadsLog;
    }

    public ArrayList<Double> getEmaLog() {
        return emaLog;
    }

    public ArrayList<Long> getCompletedTasksLog() {
        return completedTasksLog;
    }

    public ArrayList<Long> getTaskCountLog() {
        return taskCountLog;
    }
}
