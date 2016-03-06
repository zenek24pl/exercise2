package wdsr.exercise2.procon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import wdsr.exercise2.procon.OrderGeneratorModule.OrderGeneratorHelper;

public class ProducerConsumerTest {

	@Test(timeout=20000)
	public void testExchangeManualImpl() throws InterruptedException {
		// given
		Exchange exchange = new ExchangeManualImpl();
		Semaphore semaphore = new Semaphore(0);
		OrderGeneratorHelperTestImpl generatorHelper = new OrderGeneratorHelperTestImpl(semaphore);
		OrderGeneratorModule generator = new OrderGeneratorModule(generatorHelper, exchange);
		OrderMatchingModule matcher = new OrderMatchingModule(exchange);
		
		// when
		generator.start();
		matcher.start();
		
		// then
		assertTrue(semaphore.tryAcquire(OrderGeneratorModule.THREAD_COUNT, 10000, TimeUnit.SECONDS));
		TimeUnit.SECONDS.sleep(2);
		List<Order> consumedOrders = matcher.getConsumedOrders();
		assertEquals(OrderGeneratorModule.THREAD_COUNT * OrderGeneratorModule.ORDERS_PER_THREAD, consumedOrders.size());
		assertEquals(consumedOrders.size(), new HashSet<Order>(consumedOrders).size());
	}
	
	@Test(timeout=20000)
	public void testExchangeQueueImpl() throws InterruptedException {
		// given
		Exchange exchange = new ExchangeQueueImpl();
		Semaphore semaphore = new Semaphore(0);
		OrderGeneratorHelperTestImpl generatorHelper = new OrderGeneratorHelperTestImpl(semaphore);
		OrderGeneratorModule generator = new OrderGeneratorModule(generatorHelper, exchange);
		OrderMatchingModule matcher = new OrderMatchingModule(exchange);
		
		// when
		generator.start();
		matcher.start();
		
		// then
		assertTrue(semaphore.tryAcquire(OrderGeneratorModule.THREAD_COUNT, 10000, TimeUnit.SECONDS));
		TimeUnit.SECONDS.sleep(2);
		List<Order> consumedOrders = matcher.getConsumedOrders();
		assertEquals(OrderGeneratorModule.THREAD_COUNT * OrderGeneratorModule.ORDERS_PER_THREAD, consumedOrders.size());
		assertEquals(consumedOrders.size(), new HashSet<Order>(consumedOrders).size());
	}
	
	
	private class OrderGeneratorHelperTestImpl implements OrderGeneratorHelper {
		private final AtomicInteger counter;
		private final Semaphore semaphore;

		public OrderGeneratorHelperTestImpl(Semaphore semaphore) {
			this.semaphore = semaphore;
			this.counter = new AtomicInteger(0);
		}

		@Override
		public Order nextOrder() {
			return new Order(counter.incrementAndGet(), "Gold", counter.get()*10, new BigDecimal(counter.get() % 100));			
		}

		@Override
		public void generationCompleted() {
			semaphore.release();
		}
	}
}
