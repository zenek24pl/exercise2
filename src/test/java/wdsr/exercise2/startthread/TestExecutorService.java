package wdsr.exercise2.startthread;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

class TestExecutorService implements ExecutorService {
	private final ExecutorService executorService;
	private final AtomicInteger counter;
	
	public TestExecutorService() {
		this.executorService = Executors.newFixedThreadPool(5);
		this.counter = new AtomicInteger(0);
	}

	@Override
	public void execute(Runnable command) {
		throw new UnsupportedOperationException("Please use Callable objects");
	}

	@Override
	public void shutdown() {
		executorService.shutdown();
	}

	@Override
	public List<Runnable> shutdownNow() {
		return executorService.shutdownNow();
	}

	@Override
	public boolean isShutdown() {
		return executorService.isShutdown();
	}

	@Override
	public boolean isTerminated() {
		return executorService.isTerminated();
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return executorService.awaitTermination(timeout, unit);
	}

	@Override
	public <T> Future<T> submit(Callable<T> task) {
		counter.incrementAndGet();
		return executorService.submit(task);
	}

	@Override
	public <T> Future<T> submit(Runnable task, T result) {
		throw new UnsupportedOperationException("Please use Callable objects");		
	}

	@Override
	public Future<?> submit(Runnable task) {
		throw new UnsupportedOperationException("Please use Callable objects");		
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
		counter.getAndAdd(tasks.size());
		return executorService.invokeAll(tasks);
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException {
		counter.getAndAdd(tasks.size());
		return executorService.invokeAll(tasks, timeout, unit);
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
		counter.getAndAdd(tasks.size());
		return executorService.invokeAny(tasks);
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		counter.getAndAdd(tasks.size());
		return executorService.invokeAny(tasks, timeout, unit);
	}
	
	public int getNumberOfSubmittedTasks() {
		return counter.get();
	}
}
