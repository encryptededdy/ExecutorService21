# ExecutorService21
Self-adapting ExecutorService implementation.

## Benchmarker
You can run the benchmarker using the CLI provided in the Main class with the following parameters to compare different ExecutorServices against the predefined scenarios.

### ExecutorService (required)
Specify which ExecutorService to use for the benchmark.  
**Flag**:  
-e or -executor_service  
**Options**:  
fixed - FixedThreadPool from Executors factory (default size = Runtime.getRuntime().availableProcessors(), otherwise specify using -t or -num_threads flag)  
cached - CachedThreadPool from Executors factory  
moving_average - EMA implementation

### Task Granularity (required)
Specify granularity of arriving tasks to use for the benchmark.  
**Flag**:  
-g or -granularity  
**Options**:  
small - uniform small tasks  
large - uniform large tasks  
random - 9:1 ratio of small to large tasks in random order  

### Task Regularity (required)
Specify regularity of arriving tasks to use for the benchmark.  
**Flag**:  
-i or -interval  
**Options**:  
initial - all tasks arriving at the start  
fixed - all tasks arriving at fixed interval  
random - each tasks arriving at a random rate (min = 100ms, max = 1500ms)  
sine - tasks arriving at sinusoidal rate (min = 100ms, average = 750ms, max = 1400ms)  
periodic_random - tasks arriving at fixed rate, but the rate changes every 10 tasks (min = 100ms, max = 1500ms)

### Number of tasks (optional)
Specify the number of arriving tasks to use for the benchmark.  
**Flag**:  
-n or -num_tasks  
**Default**:  
100 (should always be greater than this)

### Timeout (optional)
Specify the maximum execution time for a single task.  
**Flag**:  
-timeout  
**Default**:  
10s

## Additional Data Recording
### Exponential Moving Average Logging
When enabled, this writes out a CSV file `emalog.csv` that contains the number of active threads, current EMA prediction and added/finished tasks at each interval of EMA prediction.

**To Enable**

Enable the `ENABLE_CSV_WRITE` flag in `MovingAverageAdaptiveExecutorService`, and enable `ENABLE_LOGGING` in `WatcherThread`, otherwise the CSV output will be empty.
### Latency Logging
When enabled, a CSV file is written out with rows representing the start latency of all tasks that were scheduled, measured in nanoseconds.

**To Enable**

Enable the `ENABLE_LATENCY_LOGGING` flag in `Benchmarker`