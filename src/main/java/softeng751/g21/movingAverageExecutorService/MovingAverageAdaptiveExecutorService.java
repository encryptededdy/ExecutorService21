package softeng751.g21.movingAverageExecutorService;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class MovingAverageAdaptiveExecutorService extends ThreadPoolExecutor {
    private Thread watcherThread;

    public MovingAverageAdaptiveExecutorService(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
        // We use a SynchronousQueue since we want direct handoff
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, new LinkedBlockingQueue<>());
        watcherThread = new Thread(new WatcherThread(this));
        watcherThread.start();
    }
}
