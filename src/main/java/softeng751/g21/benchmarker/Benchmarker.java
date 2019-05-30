package softeng751.g21.benchmarker;

import softeng751.g21.model.PaperSize;
import softeng751.g21.model.PieceOfPaper;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Benchmarker {

    private static final Logger logger = Logger.getLogger("Benchmarker.class");

    // Sine interval tuning
    private static final double TASKS_PER_HALF_CYCLE = 20.0; // total number of tasks in list should be at least double this.
    private static final int AVERAGE_DELAY_MS = 1000;
    private static final int MIN_DELAY_MS = 500;

    private ExecutorService executorService;
    private List<PieceOfPaper> listOfTasks;

    public Benchmarker(ExecutorService executorService, TaskGranularity granularity, int numTask) {
        this.executorService = executorService;
        this.listOfTasks = new ArrayList<>();
        initTasks(granularity, numTask);
    }

    private void initTasks(TaskGranularity granularity, int numTask) {
        switch (granularity) {
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
                for (Runnable task : listOfTasks) {
                    try {
                        logger.info("Submit a task at: " + System.currentTimeMillis());
                        executorService.submit(task);
                        Thread.sleep(new Random().nextInt(4900) + 100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case FIXED:
                for (Runnable task : listOfTasks) {
                    try {
                        logger.info("Submit a task at: " + System.currentTimeMillis());
                        executorService.submit(task);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case SINE:
                int added = 0;
                for (Runnable task : listOfTasks) {
                    logger.info("Submit a task at: " + System.currentTimeMillis());
                    executorService.submit(task);
                    added++;
                    /*
                    Every time we add a task, we increment added. Therefore, after adding TASKS_PER_HALF_CYCLE tasks,
                    we've gone from 0 -> 1 (or -1) -> 0.
                    Increment this by one so the range is 0 -> 2.
                    Multiply this my AVERAGE_DELAY_MS, so the range of delay is 0 -> AVERAGE_DELAY_MS x 2

                    Thus as we add tasks, our delay starts at AVERAGE_DELAY_MS, approaches 0, goes back up to
                    AVERAGE_DELAY_MS after added TASKS_PER_HALF_CYCLE tasks, then up to double AVERAGE_DELAY_MS and back
                    down to AVERAGE_DELAY__MS after adding 2x TASKS_PER_HALF_CYCLE tasks.
                     */
                    int sleepTime = (int) (Math.sin((added / TASKS_PER_HALF_CYCLE) * Math.PI) + 1) * (AVERAGE_DELAY_MS - MIN_DELAY_MS) + MIN_DELAY_MS;
                    try {
                        Thread.sleep(sleepTime);
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
