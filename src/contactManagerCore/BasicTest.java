package contactManagerCore;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class BasicTest {
	private Object valueExpected = 0;
	public Object valueActual = 1;
	
	@Test
	public void test() {
		assertEquals(valueExpected, valueActual);
		valueActual = 0;
	}
}
