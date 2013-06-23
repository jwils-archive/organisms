package organisms.g4.comm;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import organisms.Constants;

public class DataForChildTest implements Constants {
	DataForChild d1;

	@Before
	public void setUp() throws Exception {
		d1 = new DataForChild(NORTH,true,4,3);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		assertEquals("ENCODE DECODE FAILED", d1, DataForChild.decode(d1.encode()));
	}

}
