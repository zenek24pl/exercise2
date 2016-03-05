package wdsr.exercise2.counter;

import org.junit.Before;

public class CountingFacadeWithAtomicTest extends CountingFacadeTest {	
	@Before
	public void setUp() {
		super.setUp();
		facade = new CountingFacadeWithAtomic(businessService);
	}
}
