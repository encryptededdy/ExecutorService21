package softeng751.g21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Benchmarker {

    public void start(final ExecutorService executorService, final int timeout, final int numTask) {

        // Task creation
        for (int i = 1; i <= numTask; i++) {
            if (i % 10 == 0) {
                executorService.submit(new PieceOfPaper(PaperSize.A3, i));
            } else {
                executorService.submit(new PieceOfPaper(PaperSize.A6, i));
            }
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(timeout, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
