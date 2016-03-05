package wdsr.exercise2.counter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public abstract class CountingFacadeTest {
	@Mock
	protected BusinessService businessService;
	
	protected CountingFacade facade; 
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCountMechanism() {
		// when
		facade.countAndInvoke();
		facade.countAndInvoke();
		
		// then
		assertEquals(2, facade.getCount());
	}

	@Test
	public void testShouldInvokeBusinessService() {
		// when
		facade.countAndInvoke();
		facade.countAndInvoke();
		
		// then
		verify(businessService, times(2)).executeAction();
	}

	@Test
	public void testThreadSafety() throws InterruptedException {
		// given
		int numberOfThreads = 10;
		int invocationsPerThread = 10000;
		Thread[] threads = createInvokerThreads(numberOfThreads, invocationsPerThread);		
		
		// when
		startInvokerThreads(threads);
		waitForInvokerThreadsToComplete(threads);
		
		// then
		assertEquals(numberOfThreads*invocationsPerThread, facade.getCount());
	}

	private Thread[] createInvokerThreads(int numberOfThreads, int invocationsPerThread) {
		Thread[] result = new Thread[numberOfThreads];
		
		Runnable invokeTask = () -> {
			for (int i=0; i<invocationsPerThread; i++) {
				facade.countAndInvoke();
			}
		};
		for (int i=0; i<numberOfThreads; i++) {
			result[i] = new Thread(invokeTask);
		}
		return result;
	}

	private void startInvokerThreads(Thread[] threads) {
		for (Thread t: threads) {
			t.start();
		}
	}

	private void waitForInvokerThreadsToComplete(Thread[] threads) throws InterruptedException {
		for (Thread t: threads) {
			t.join();
		}
	}
}
