package softeng751.g21;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class CustomExecutorService implements ExecutorService {
    @Override
    public void shutdown() {

    }

    @Override
    public List<Runnable> shutdownNow() {
        return null;
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        // TODO low priority method unless we want to get some return value for the task
        return null;
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        // TODO same as below but returning result (same as method above which takes in the callable)
        return null;
    }

    @Override
    public Future<?> submit(Runnable task) {
        // TODO implement this function for handling individual tasks as incoming events, can queue up task and return futures.
        return null;
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        // TODO implement this function for invokving initial set of tasks
        return null;
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        // TODO same as above but taking in a timeout
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        // TODO low priority method
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        // TODO low priority method
        return null;
    }

    @Override
    public void execute(Runnable command) {
        // TODO executes the runnable straight away instead of possibly queueing up tasks (submit())
    }
}
