package wdsr.exercise2.startthread;

import java.util.concurrent.Semaphore;

class FibonacciTestCallback implements FibonacciCallback {
	private final Thread creationThread;
	private final Semaphore semaphore;
	
	public FibonacciTestCallback(Thread creationThread, Semaphore semaphore) {
		this.creationThread = creationThread;
		this.semaphore = semaphore;
	}
	
	public void fibonacciComputed(long value) {
		ensureAsyncInvocation();
		semaphore.release();
	}
	
	private void ensureAsyncInvocation() {
		if (Thread.currentThread() == creationThread) {
			throw new AssertionError("Computation must be invoked asynchronously");
		}
	}	
}
