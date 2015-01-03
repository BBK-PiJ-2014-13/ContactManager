package contactManagerCore;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class BasicTest {
	public Object valueExpected = 1;
	public Object valueActual = 0;
	
	public void test() {
		assertEquals(valueExpected, valueActual);
		valueExpected = 1;
		valueActual = 0;
	}
}
