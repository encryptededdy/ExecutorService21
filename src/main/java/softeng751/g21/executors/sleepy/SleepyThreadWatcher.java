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

    public SleepyThreadWatcher(SleepyExecutorService parent) {
        this.parent = parent;
        this.bean = ManagementFactory.getThreadMXBean();
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            Collection<?> workers = getWorkers();

            if (workers != null) {
                for (Object worker : workers) {
                    Long id = getId(worker);
                    Boolean active = isActive(worker);

                    if (id == null || active == null || !active) {
                        continue;
                    }

                    long oldTime = cpuTimes.getOrDefault(id, 0L);
                    long newTime = bean.getThreadCpuTime(id);
                    // Spent no time on CPU, probably sleeping
                    if (newTime - oldTime == 0) {
                        System.out.println("[" + id + "] is probably sleeping");
                    }

                    cpuTimes.put(id, newTime);
                }
            }

            try {
                Thread.sleep(500);
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
