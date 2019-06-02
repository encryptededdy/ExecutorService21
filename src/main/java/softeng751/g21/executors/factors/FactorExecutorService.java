package softeng751.g21.executors.factors;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FactorExecutorService extends ThreadPoolExecutor {

    private final ThreadCountWatcher watcher;

    private final Thread watcherThread;

    public FactorExecutorService() {
        this(Runtime.getRuntime().availableProcessors(), 200, 10, TimeUnit.SECONDS);
    }

    public FactorExecutorService(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, new LinkedBlockingQueue<>());

        ThreadCountWatcher watcher = new ThreadCountWatcher(this);
        Thread thread = new Thread(watcher);
        thread.start();

        this.watcher = watcher;
        this.watcherThread = thread;
    }

    public void addThreadCountFactor(ThreadCountFactor factor) {
        watcher.addThreadCountFactor(factor);
    }

}
