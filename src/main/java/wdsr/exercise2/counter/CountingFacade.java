package wdsr.exercise2.counter;

import java.util.concurrent.atomic.AtomicInteger;

public interface CountingFacade {
	void countAndInvoke();
	int getCount();
}
