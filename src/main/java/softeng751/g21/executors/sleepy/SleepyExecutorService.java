package softeng751.g21.executors.sleepy;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SleepyExecutorService extends ThreadPoolExecutor {

    public SleepyExecutorService(int size) {
        super(size, size, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

        SleepyThreadWatcher watcher = new SleepyThreadWatcher(this);
        new Thread(watcher).start();
    }

}
