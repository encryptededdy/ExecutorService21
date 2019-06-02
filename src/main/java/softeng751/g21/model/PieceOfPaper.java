package softeng751.g21.model;

import softeng751.g21.benchmarker.LatencyMonitor;

import java.time.Instant;
import java.util.concurrent.Callable;

public class PieceOfPaper implements Runnable, Callable<Void> {
    private int taskID;
    private PaperSize paperSize;
    private Long startTime;

    public PieceOfPaper(PaperSize paperSize, int taskID) {
        this.taskID = taskID;
        this.paperSize = paperSize;
    }

    public void startLatencyTiming() {
        startTime = System.nanoTime();
    }

    @Override
    public void run() {
        LatencyMonitor.getInstance().addEntry(startTime, System.nanoTime());
        System.out.println(Thread.currentThread().getName() + " is now executing task " + taskID);

        for (int i = 0; i < Integer.MAX_VALUE * paperSize.getFraction(); i++) {
            // Franklin's Law
            double memes = 4.0 * Math.PI / -Math.acos(Math.PI) * Math.scalb(2, 5);
        }
    }

    @Override
    public Void call() throws Exception {
        run();
        return null;
    }
}
