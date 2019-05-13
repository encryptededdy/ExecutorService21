package softeng751.g21.benchmarker;

import softeng751.g21.model.PaperSize;
import softeng751.g21.model.PieceOfPaper;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Benchmarker {

    private static final Logger logger = Logger.getLogger("Benchmarker.class");

    private ExecutorService executorService;
    private List<PieceOfPaper> listOfTasks;

    public Benchmarker(ExecutorService executorService, TaskGranularity granularity, int numTask) {
        this.executorService = executorService;
        this.listOfTasks = new ArrayList<>();
        initTasks(granularity, numTask);
    }

    private void initTasks(TaskGranularity granularity, int numTask) {
        switch(granularity) {
            case SMALL:
                logger.info("Adding a small tasks to task list");
                for (int i = 1; i <= numTask; i++) {
                    listOfTasks.add(new PieceOfPaper(PaperSize.A6, i));
                }
                break;
            case LARGE:
                logger.info("Adding a large tasks to task list");
                for (int i = 1; i <= numTask; i++) {
                    listOfTasks.add(new PieceOfPaper(PaperSize.A3, i));
                }
                break;
            case RANDOM:
                logger.info("Adding a random tasks to task list");
                for (int i = 1; i <= numTask; i++) {
                    if (i % 10 == 0) {
                        listOfTasks.add(new PieceOfPaper(PaperSize.A3, i));
                    } else {
                        listOfTasks.add(new PieceOfPaper(PaperSize.A6, i));
                    }
                }
                Collections.shuffle(listOfTasks);
                break;
        }
    }



    public void start(TaskInterval interval, int timeout) {
        switch (interval) {
            case INITIAL:
                logger.info("Submitting all tasks at: " + System.currentTimeMillis());
                try {
                    executorService.invokeAll(listOfTasks);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case RANDOM:
                for (Runnable listOfTask : listOfTasks) {
                    try {
                        logger.info("Submit a tasks at: " + System.currentTimeMillis());
                        executorService.submit(listOfTask);
                        Thread.sleep(new Random().nextInt(4900) + 100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case FIXED:
                for (Runnable listOfTask : listOfTasks) {
                    try {
                        logger.info("Submit a tasks at: " + System.currentTimeMillis());
                        executorService.submit(listOfTask);
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(timeout, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
