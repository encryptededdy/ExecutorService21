package softeng751.g21.movingAverageExecutorService;

import java.util.concurrent.Callable;

public class WatcherThread implements Runnable, Callable<Void> {
    private static final int POLL_DELAY_MS = 100;

    private MovingAverageAdaptiveExecutorService service;

    public WatcherThread(MovingAverageAdaptiveExecutorService service) {
        this.service = service;
    }

    @Override
    public void run() {
        // TODO: Handle shutdown
        System.out.println("Watcher thread started");
        for (;;) {

            try {
                Thread.sleep(POLL_DELAY_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Void call() throws Exception {
        run();
        return null;
    }
}
