package softeng751.g21;

import org.apache.commons.cli.*;
import softeng751.g21.benchmarker.Benchmarker;
import softeng751.g21.benchmarker.TaskGranularity;
import softeng751.g21.benchmarker.TaskInterval;
import softeng751.g21.movingAverageExecutorService.MovingAverageAdaptiveExecutorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger("Main.class");

    private static int DEFAULT_TIMEOUT = 10;
    private static int DEFAULT_NUM_TASKS = 100;

    public static void main(String[] args) {

        Options options = setCommandLineOptions();
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);
            int timeout = cmd.hasOption("timeout") ? Integer.parseInt(cmd.getOptionValue("timeout")) : DEFAULT_TIMEOUT;
            int numTasks = cmd.hasOption("n") ? Integer.parseInt(cmd.getOptionValue("n")) : DEFAULT_NUM_TASKS;

            ExecutorService executorService = createExecutorService(cmd);
            Benchmarker benchmarker = createBenchmarker(cmd, executorService, numTasks);
            startBenchmarker(cmd, benchmarker, timeout);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static Options setCommandLineOptions() {
        Options options = new Options();

        Option executorServiceOption = new Option("e", "executor_service", true, "which ExecutorService implementation to use");
        executorServiceOption.setRequired(true);
        options.addOption(executorServiceOption);

        Option timeoutOption = new Option("timeout", "timeout", true, "the maximum time to wait");
        options.addOption(timeoutOption);

        Option numTasksOption = new Option("n", "num_tasks", true, "number of tasks to execute");
        options.addOption(numTasksOption);

        Option granularityOption = new Option("g", "granularity", true, "granularity of tasks (small, large, random)");
        options.addOption(granularityOption);

        Option intervalOption = new Option("i", "interval", true, "frequency of tasks (initial, fixed, random)");
        options.addOption(intervalOption);

        Option numThreadsOption = new Option("t", "num_threads", true, "number of threads to use for FixedThreadPool");
        options.addOption(numThreadsOption);

        return options;
    }

    private static ExecutorService createExecutorService(CommandLine cmd) throws ParseException {
        switch (cmd.getOptionValue("e")) {
            case ("fixed"):
                int threads = cmd.hasOption("t") ? Integer.parseInt(cmd.getOptionValue("t")) : Runtime.getRuntime().availableProcessors();
                return Executors.newFixedThreadPool(threads);
            case ("cached"):
                return Executors.newCachedThreadPool();
            case ("moving_average"):
                return new MovingAverageAdaptiveExecutorService(1, 1, TimeUnit.SECONDS);
            default:
                throw new ParseException("invalid ExecutorService type specified (fixed, cached, moving_average)");
        }
    }

    private static Benchmarker createBenchmarker(CommandLine cmd, ExecutorService executorService, int numTasks) throws ParseException {
        switch (cmd.getOptionValue("g")) {
            case ("small"):
                return new Benchmarker(executorService, TaskGranularity.SMALL, numTasks);
            case ("large"):
                return new Benchmarker(executorService, TaskGranularity.LARGE, numTasks);
            case ("random"):
                return new Benchmarker(executorService, TaskGranularity.RANDOM, numTasks);
            default:
                throw new ParseException("invalid frequency of task specified (initial, fixed, random)");
        }
    }

    private static void startBenchmarker(CommandLine cmd, Benchmarker benchmarker, int timeout) throws ParseException {
        switch (cmd.getOptionValue("i")) {
            case ("initial"):
                benchmarker.start(TaskInterval.INITIAL, timeout);
                return;
            case ("fixed"):
                benchmarker.start(TaskInterval.FIXED, timeout);
                return;
            case ("random"):
                benchmarker.start(TaskInterval.RANDOM, timeout);
                return;
            case ("sine"):
                benchmarker.start(TaskInterval.SINE, timeout);
                return;
            case ("periodic_random"):
                logger.info("Note: periodic_random only works well with large number of tasks (e.g. >100)");
                benchmarker.start(TaskInterval.PERIODIC_RANDOM, timeout);
                return;
            default:
                throw new ParseException("invalid frequency of task specified (initial, fixed, random, sine, periodic_random)");
        }
    }
}
