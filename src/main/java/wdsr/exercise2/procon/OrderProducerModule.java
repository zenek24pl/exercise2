package wdsr.exercise2.procon;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderProducerModule {
	static final int THREAD_COUNT = 5;
	static final int ORDERS_PER_THREAD = 100000;

	private OrderProducerHelper helper;
	private ExecutorService executorService;
	private Buffer exchange;
	
	public OrderProducerModule(OrderProducerHelper helper, Buffer exchange) {
		this.helper = helper;
		this.exchange = exchange;
		this.executorService = Executors.newFixedThreadPool(THREAD_COUNT);
	}
	
	public void start() {
		Callable<Void> callable = new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				for (int j=0; j<ORDERS_PER_THREAD; j++) {
					exchange.submitOrder(helper.nextOrder());
				}
				helper.generationCompleted();
				return null;
			}			
		};
		
		for (int i=0; i<THREAD_COUNT; i++) {
			executorService.submit(callable);
		}
	}

	public static interface OrderProducerHelper {
		Order nextOrder();
		void generationCompleted();
	}
}
