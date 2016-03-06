package wdsr.exercise2.procon;

/**
 * Task: implement Exchange interface without using any *Queue classes from java.util.concurrent package.
 * Any combination of "synchronized", *Lock, *Semaphore, *Condition, *Barrier, *Latch is allowed.
 */
public class ExchangeManualImpl implements Exchange {
	public void submitOrder(Order order) throws InterruptedException {
		// TODO
	}
	
	public Order consumeNextOrder() throws InterruptedException {
		// TODO
		return null;
	}
}
