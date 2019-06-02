package softeng751.g21.executors.sleepy;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SleepyExecutorService extends ThreadPoolExecutor {

    public SleepyExecutorService() {
        this(Runtime.getRuntime().availableProcessors(), 200, 10, TimeUnit.SECONDS);
    }

    public SleepyExecutorService(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, new LinkedBlockingQueue<>());

        SleepyThreadWatcher watcher = new SleepyThreadWatcher(this);
        new Thread(watcher).start();
    }

}
