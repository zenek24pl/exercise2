package wdsr.exercise2.counter;

import org.junit.Before;

public class CountingFacadeWithLockTest extends CountingFacadeTest {	
	@Before
	public void setUp() {
		super.setUp();
		facade = new CountingFacadeWithLock(businessService);
	}
}
