package softeng751.g21.movingAverageExecutorService;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
            printToCSV();
            return true;
        }
        return false;
    }

    private void printToCSV() {
        ArrayList<Integer> activeThreadsLog = watcherThread.getActiveThreadsLog();
        ArrayList<Double> emaLog = watcherThread.getEmaLog();
        ArrayList<Long> completedLog = watcherThread.getCompletedTasksLog();
        ArrayList<Long> scheduledLog = watcherThread.getTaskCountLog();

        try (CSVPrinter printer = new CSVPrinter(new FileWriter("emaLog4.csv"), CSVFormat.EXCEL)) {
            printer.printRecord("Active Threads", "EMA", "Completed Tasks", "Scheduled Tasks");
            for (int i = 0; i < activeThreadsLog.size() ; i++) {
                printer.printRecord(activeThreadsLog.get(i), emaLog.get(i), completedLog.get(i), scheduledLog.get(i));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}