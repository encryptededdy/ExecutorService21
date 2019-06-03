package softeng751.g21.executors.factors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

class ThreadCountWatcher implements Runnable {

    private final ThreadPoolExecutor parent;

    private final List<ThreadCountFactor> tcFactors;

    private final List<Integer> pTicks;

    ThreadCountWatcher(ThreadPoolExecutor parent) {
        this.parent = parent;

        this.tcFactors = Collections.synchronizedList(new ArrayList<>());
        this.pTicks = Collections.synchronizedList(new ArrayList<>());
    }

    public void addThreadCountFactor(ThreadCountFactor factor) {
        tcFactors.add(factor);
        pTicks.add(0);
    }

    private boolean quickRampUp = true;

    private boolean reduceThreadCount;

    private int reduceFromThreadCount;

    private int ticks = 0;

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            ticks++;

            // Sleep while there is nothing to do
            if (tcFactors.isEmpty()) {
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            boolean reduceCheck = false;

            for (int i = 0; i < tcFactors.size(); i++) {
                Integer ticks = pTicks.get(i);
                ticks = ticks != null ? ticks : 0;

                ThreadCountFactor factor = tcFactors.get(i);
                int newTicks = factor.hasPerformanceDeteriorated() ? Math.min(30, ticks + 1) : Math.max(0, ticks - 1);
                pTicks.set(i, newTicks);

                // Once the performance has deteriorated for 2.5 seconds
                if (newTicks >= 25) {
                    if (!reduceThreadCount) {
                        System.out.println("Performance decoration detected at " + parent.getActiveCount() + " threads");
                    }
                    quickRampUp = false;
                    reduceCheck = true;
                    reduceThreadCount = true;
                    reduceFromThreadCount = parent.getActiveCount();
                }
            }

            // None of the factors are saying to reduce the thread count
            if (!reduceCheck && reduceThreadCount) {
                System.out.println("Performance is back to normal at " + parent.getActiveCount() + " threads");
                reduceThreadCount = false;
            }

            if (reduceThreadCount) {
                // Reduce the number of threads by one
                if (parent.getActiveCount() >= reduceFromThreadCount && (ticks % 10) == 0) {
                    parent.setCorePoolSize(reduceFromThreadCount - 1);
                    parent.setMaximumPoolSize(reduceFromThreadCount - 1);
                    System.out.println("Reducing max threads to " + parent.getMaximumPoolSize());
                }
            } else {
                // Spawn new threads every so often if there are queued tasks
                if (!parent.getQueue().isEmpty() && ticks % (quickRampUp ? 5 : 50) == 0) {
                    parent.setCorePoolSize(parent.getActiveCount() + 1);
                    parent.setMaximumPoolSize(parent.getActiveCount() + 1);
                    System.out.println("Incrementing max threads to " + parent.getMaximumPoolSize());
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
