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

import wdsr.exercise2.procon.OrderProducerModule.OrderProducerHelper;

public class ProducerConsumerTest {

	@Test(timeout=20000)
	public void testBufferManualImpl() throws InterruptedException {
		// given
		Buffer buffer = new BufferManualImpl();
		Semaphore semaphore = new Semaphore(0);
		OrderProducerHelperTestImpl producerHelper = new OrderProducerHelperTestImpl(semaphore);
		OrderProducerModule producer = new OrderProducerModule(producerHelper, buffer);
		OrderConsumerModule consumer = new OrderConsumerModule(buffer);
		
		// when
		producer.start();
		consumer.start();
		
		// then
		assertTrue(semaphore.tryAcquire(OrderProducerModule.THREAD_COUNT, 10000, TimeUnit.SECONDS));
		TimeUnit.SECONDS.sleep(2);
		List<Order> consumedOrders = consumer.getConsumedOrders();
		assertEquals(OrderProducerModule.THREAD_COUNT * OrderProducerModule.ORDERS_PER_THREAD, consumedOrders.size());
		assertEquals(consumedOrders.size(), new HashSet<Order>(consumedOrders).size());
	}
	
	@Test(timeout=20000)
	public void testBufferQueueImpl() throws InterruptedException {
		// given
		Buffer buffer = new BufferQueueImpl();
		Semaphore semaphore = new Semaphore(0);
		OrderProducerHelperTestImpl producerHelper = new OrderProducerHelperTestImpl(semaphore);
		OrderProducerModule producer = new OrderProducerModule(producerHelper, buffer);
		OrderConsumerModule consumer = new OrderConsumerModule(buffer);
		
		// when
		producer.start();
		consumer.start();
		
		// then
		assertTrue(semaphore.tryAcquire(OrderProducerModule.THREAD_COUNT, 10000, TimeUnit.SECONDS));
		TimeUnit.SECONDS.sleep(2);
		List<Order> consumedOrders = consumer.getConsumedOrders();
		assertEquals(OrderProducerModule.THREAD_COUNT * OrderProducerModule.ORDERS_PER_THREAD, consumedOrders.size());
		assertEquals(consumedOrders.size(), new HashSet<Order>(consumedOrders).size());
	}
	
	
	private class OrderProducerHelperTestImpl implements OrderProducerHelper {
		private final AtomicInteger counter;
		private final Semaphore semaphore;

		public OrderProducerHelperTestImpl(Semaphore semaphore) {
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
