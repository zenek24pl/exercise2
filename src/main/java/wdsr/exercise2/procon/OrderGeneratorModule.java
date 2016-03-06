package wdsr.exercise2.procon;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderGeneratorModule {
	static final int THREAD_COUNT = 5;
	static final int ORDERS_PER_THREAD = 100000;

	private OrderGeneratorHelper helper;
	private ExecutorService executorService;
	private Exchange exchange;
	
	public OrderGeneratorModule(OrderGeneratorHelper helper, Exchange exchange) {
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

	public static interface OrderGeneratorHelper {
		Order nextOrder();
		void generationCompleted();
	}
}
