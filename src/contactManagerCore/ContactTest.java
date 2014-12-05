package contactManagerCore;

import org.junit.Test;

public class ContactTest extends BasicTest {
	ContactImpl contact = new ContactImpl(10, "John");
	
	public void testsGetId() {
		valueExpected = 10;
		valueActual = contact.getId();
		test();
	}
	
	public void testsGetName() {
		valueExpected = "John";
		valueActual = contact.getName();
		test();
	}
	

}
