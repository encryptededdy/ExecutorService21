package softeng751.g21.movingAverageExecutorService;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class MovingAverageAdaptiveExecutorService extends ThreadPoolExecutor {
    private WatcherThread watcherThread;
    private Thread watcherThreadThread;

    public MovingAverageAdaptiveExecutorService(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
        // We use a SynchronousQueue since we want direct handoff
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, new LinkedBlockingQueue<>());
        watcherThread = new WatcherThread(this);
        watcherThreadThread = new Thread(watcherThread);
        watcherThreadThread.start();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        if (super.awaitTermination(timeout, unit)) {
            watcherThread.requestTermination();
            watcherThreadThread.join();
            return true;
        }
        return false;
    }
}