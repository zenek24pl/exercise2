package wdsr.exercise2.counter;

/**
 * Created by Marek on 05.03.2016.
 * 
 * Task: use {@see java.util.concurrent.atomic.AtomicInteger} to make CountingFacadeWithAtomicTest pass. 
 */
public class CountingFacadeWithAtomic implements CountingFacade {
	private final BusinessService businessService;
	
	private int invocationCounter;
	
	public CountingFacadeWithAtomic(BusinessService businessService) {
		this.businessService = businessService;
	}
		
	public void countAndInvoke() {
		invocationCounter++;
		businessService.executeAction();
	}
	
	public int getCount() {
		return invocationCounter;
	}
}
