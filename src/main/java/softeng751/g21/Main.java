package softeng751.g21;

import org.apache.commons.cli.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static int DEFAULT_TIMEOUT = 10;
    private static int DEFAULT_NUM_TASKS = 100;

    public static void main(String[] args) {
        Options options = new Options();

        Option executorServiceOption = new Option("e", "executor_service", true, "which ExecutorService implementation to use");
        executorServiceOption.setRequired(true);
        options.addOption(executorServiceOption);

        Option timeoutOption = new Option("timeout", "timeout", true, "the maximum time to wait");
        options.addOption(timeoutOption);

        Option numTasksOption = new Option("n", "num_tasks", true, "number of tasks to execute");
        options.addOption(numTasksOption);

        Option numThreadsOption = new Option("t", "num_threads", true, "number of threads to use for FixedThreadPool");
        options.addOption(numThreadsOption);

        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);

            ExecutorService executorService;
            switch (cmd.getOptionValue("e")) {
                case ("fixed"):
                    int threads = cmd.hasOption("t") ? Integer.parseInt(cmd.getOptionValue("t")) : Runtime.getRuntime().availableProcessors();
                    executorService = Executors.newFixedThreadPool(threads);
                    break;
                case ("cache"):
                    executorService = Executors.newCachedThreadPool();
                    break;
                case ("custom"):
                    executorService = new CustomExecutorService();
                    break;
                default:
                    throw new ParseException("invalid ExecutorService type specified (fixed, cache, custom)");
            }

            int timeout = cmd.hasOption("timeout") ? Integer.parseInt(cmd.getOptionValue("timeout")) : DEFAULT_TIMEOUT;
            int numTasks = cmd.hasOption("n") ? Integer.parseInt(cmd.getOptionValue("n")) : DEFAULT_NUM_TASKS;

            Benchmarker benchmarker = new Benchmarker();
            benchmarker.start(executorService, numTasks, timeout);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
