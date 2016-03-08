package wdsr.exercise2.procon;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class OrderConsumerModule {
	private final Buffer exchange;
	private final List<Order> consumedOrders;
	
	public OrderConsumerModule(Buffer exchange) {
		this.exchange = exchange;
		this.consumedOrders = Collections.synchronizedList(new LinkedList<>());
	}
		
	public void start() {
		Callable<Void> callable = new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				while (true) {
					Order order = exchange.consumeNextOrder();
					if (order != null) {
						consumedOrders.add(order);
					}
				}
			}			
		};
		
		Executors.newSingleThreadExecutor().submit(callable);
	}
		
	public List<Order> getConsumedOrders() {
		return consumedOrders;
	}
}
