package softeng751.g21.executors.sleepy;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

public class SleepyThreadWatcher implements Runnable {

    private final SleepyExecutorService parent;

    private final ThreadMXBean bean;

    private final Map<Long, Long> cpuTimes = new HashMap<>();

    private final int defaultPoolSize;

    public SleepyThreadWatcher(SleepyExecutorService parent) {
        this.parent = parent;
        this.bean = ManagementFactory.getThreadMXBean();
        this.defaultPoolSize = parent.getMaximumPoolSize();
    }

    @Override
    public void run() {
        int sleep = 300;

        while (!Thread.interrupted()) {
            Collection<?> workers = getWorkers();

            double utilization = 0.0;
            double maxUtilization = 0.0;

            if (workers != null) {
                for (Object worker : workers) {
                    Long id = getId(worker);
                    Boolean active = isActive(worker);

                    if (id == null || active == null || !active) {
                        continue;
                    }

                    long oldTime = cpuTimes.getOrDefault(id, 0L);
                    long newTime = bean.getThreadCpuTime(id);

                    cpuTimes.put(id, newTime);

                    // Spent no time on CPU, probably sleeping
                    if (newTime - oldTime == 0) {
                        System.out.println("[" + id + "] is probably sleeping");
                    }

                    double percentage = Math.max(0.0, newTime - oldTime) / sleep / 1000000.0;
                    if (percentage >= 1.0) percentage = 1.0;
                    utilization += percentage;

                    // Use Math.min to ensure we only utilize up to the default thread pool size
                    maxUtilization = Math.min(maxUtilization + 1.0, 1.0 * defaultPoolSize);
                }
            }

            int poolSize = parent.getMaximumPoolSize();
            double fractionUtilization = utilization / maxUtilization;

            int supplementary = (int) ((1.0 - fractionUtilization) * poolSize);

            if (supplementary != 0) {
                if (parent.getQueue().isEmpty() && supplementary > 0) {
                    // Do NOT increase thread count if nothing is in queue
                } else {
                    System.out.println("Spawning " + supplementary + " more threads");
                    parent.setCorePoolSize(poolSize + supplementary);
                    parent.setMaximumPoolSize(poolSize + supplementary);
                }
            }

            if (!Double.isNaN(fractionUtilization)) {
                System.out.println("Utilization: " + fractionUtilization);
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Collection<?> getWorkers() {
        try {
            Field workers = ThreadPoolExecutor.class.getDeclaredField("workers");
            workers.setAccessible(true);
            return (Collection<?>) workers.get(parent);
        } catch (ReflectiveOperationException | ClassCastException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Long getId(Object worker) {
        try {
            Field thread = worker.getClass().getDeclaredField("thread");
            thread.setAccessible(true);
            Thread t = (Thread) thread.get(worker);
            return t.getId();
        } catch (ReflectiveOperationException | ClassCastException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Boolean isActive(Object worker) {
        try {
            Method isLocked = worker.getClass().getDeclaredMethod("isLocked");
            isLocked.setAccessible(true);
            return (boolean) isLocked.invoke(worker);
        } catch (ReflectiveOperationException | ClassCastException e) {
            e.printStackTrace();
        }

        return null;
    }

}
