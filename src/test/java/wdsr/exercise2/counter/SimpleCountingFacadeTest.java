package wdsr.exercise2.counter;

import org.junit.Before;

public class SimpleCountingFacadeTest extends CountingFacadeTest {	
	@Before
	public void setUp() {
		super.setUp();
		facade = new SimpleCountingFacade(businessService);
	}
}
