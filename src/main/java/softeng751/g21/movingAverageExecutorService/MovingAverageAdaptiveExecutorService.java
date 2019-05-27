package softeng751.g21.movingAverageExecutorService;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MovingAverageAdaptiveExecutorService extends ThreadPoolExecutor {
    public MovingAverageAdaptiveExecutorService(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
        // We use a SynchronousQueue since we want direct handoff
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, new SynchronousQueue<Runnable>());

    }
}
