package wdsr.exercise2.procon;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Task: implement Buffer interface without using any *Queue classes from java.util.concurrent package.
 * Any combination of "synchronized", *Lock, *Semaphore, *Condition, *Barrier, *Latch is allowed.
 */
public class BufferManualImpl implements Buffer {

	private final Queue<Order> orderQueue=new LinkedList<>();
	private Order order;
	private final Lock lock=new ReentrantLock();
	private boolean available=true;
	private final Condition notFull=lock.newCondition();
	//Order order=new Order();
	private final Condition notEmpty=lock.newCondition();
	
	
public void submitOrder(Order order) throws InterruptedException {
		// TODO
	lock.lock();
	try{
		while(!available){
			notEmpty.await();
		}
		orderQueue.add(order);
		notFull.signal();
		available=false;
	}finally{
		lock.unlock();
	}
	
	}
	
	public Order consumeNextOrder() throws InterruptedException {
		lock.lock();
		try{
			while(available){
				notFull.await();
			}
			order=orderQueue.remove();
			notEmpty.signal();
			available=true;
		}finally{
			lock.unlock();
		}
		return order ;
	}
}
