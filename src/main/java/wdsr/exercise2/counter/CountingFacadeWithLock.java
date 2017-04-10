package wdsr.exercise2.counter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Marek on 05.03.2016.
 * 
 * Task: use {@see java.util.concurrent.locks.Lock} to make CountingFacadeWithLockTest pass. 
 */
public class CountingFacadeWithLock implements CountingFacade {
	private final BusinessService businessService;
	private Lock lock;
	private int invocationCounter;
	
	public CountingFacadeWithLock(BusinessService businessService) {
		this.businessService = businessService;
		this.lock=new ReentrantLock();
	}
		
	public void countAndInvoke() {
		try{
			lock.lock();
		invocationCounter++;
		}finally{
			lock.unlock();
		}
		businessService.executeAction();
	}
	
	public int getCount() {
		return invocationCounter;
	}
}
