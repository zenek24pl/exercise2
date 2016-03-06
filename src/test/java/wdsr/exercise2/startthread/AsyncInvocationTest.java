package wdsr.exercise2.startthread;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class AsyncInvocationTest {

	@Test
	public void testBusinessServiceWithThreadAndRunnable_computationAndCallbackShouldBeInvokedInANewThread() throws InterruptedException {
		// given
		Thread currentThread = Thread.currentThread();
		Semaphore semaphore = new Semaphore(0);
		FibonacciCallback callback = new FibonacciTestCallback(currentThread, semaphore);
		NumericHelper helper = new NumericTestHelper(currentThread);
		BusinessServiceWithThreadAndRunnable businessService = new BusinessServiceWithThreadAndRunnable(helper);
		
		// when
		businessService.computeFibonacci(5, callback);
		
		// then
		assertTrue("Callback not invoked", semaphore.tryAcquire(3, TimeUnit.SECONDS));
	}

	@Test
	public void testBusinessServiceWithExecutor_computationAndCallbackShouldBeInvokedInANewThread() throws InterruptedException {
		// given
		Thread currentThread = Thread.currentThread();
		Semaphore semaphore = new Semaphore(0);
		FibonacciCallback callback = new FibonacciTestCallback(currentThread, semaphore);
		NumericHelper helper = new NumericTestHelper(currentThread);
		BusinessServiceWithExecutor businessService = new BusinessServiceWithExecutor(helper);
		
		// when
		businessService.computeFibonacci(5, callback);
		
		// then
		assertTrue("Callback not invoked", semaphore.tryAcquire(3, TimeUnit.SECONDS));
	}
	
	@Test
	public void testBusinessServiceWithCallable_properValueShouldBeInvoked() throws InterruptedException, ExecutionException {
		// given
		Thread currentThread = Thread.currentThread();
		NumericTestHelper helper = new NumericTestHelper(currentThread);
		TestExecutorService executorService = new TestExecutorService();
		BusinessServiceWithCallable businessService = new BusinessServiceWithCallable(executorService, helper);
		
		// when
		long result = businessService.sumOfRandomInts();
		
		// then
		assertEquals(100, executorService.getNumberOfSubmittedTasks());
		assertEquals(helper.getSumOfGeneratedNumbers(), result);
	}
}
