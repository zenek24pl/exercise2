package wdsr.exercise2.procon;

public interface Buffer {
	/**
	 * Submits an order to the exchange. 
	 * If there is no space available to store the new order then this method blocks 
	 * until space is available.  
	 * @param order Submitted order.
	 */
	void submitOrder(Order order) throws InterruptedException;
	
	/**
	 * Returns one of submitted orders. The returned order is removed from this exchange.
	 * If there are no available orders then this method blocks until there is a submitted order available.
	 * @return Order instance, never null.
	 */
	Order consumeNextOrder() throws InterruptedException;
}
