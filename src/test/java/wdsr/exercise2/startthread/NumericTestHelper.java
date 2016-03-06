package wdsr.exercise2.startthread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class NumericTestHelper implements NumericHelper {
	private final Thread creationThread;
	private Random random;
	
	private List<Integer> generatedInts;

	
	public NumericTestHelper(Thread creationThread) {
		this.creationThread = creationThread;
		this.random = new Random();
		this.generatedInts = Collections.synchronizedList(new ArrayList<>());
	}

	@Override
	public long findFibonacciValue(int n) {
		ensureAsyncInvocation();
		
		if (n == 0) {
			return 0;
		}
		long nMinus2 = 0;
		long nMinus1 = 1;
		long result = 1;
		for (int i=1; i<n; i++) {
			result = nMinus2 + nMinus1;
			nMinus2 = nMinus1;
			nMinus1 = result;
		}
		return result;
	}

	@Override
	public int nextRandom() {
		ensureAsyncInvocation();
		
		int result = random.nextInt(1000);
		generatedInts.add(result);
		return result;
	}
	
	long getSumOfGeneratedNumbers() {
		return generatedInts.stream().mapToInt(Integer::intValue).sum();
	}
	
	private void ensureAsyncInvocation() {
		if (Thread.currentThread() == creationThread) {
			throw new AssertionError("Computation must be invoked asynchronously");
		}
	}

}
