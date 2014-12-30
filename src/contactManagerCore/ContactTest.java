package contactManagerCore;

import org.junit.Test;

public class ContactTest extends BasicTest {
	ContactImpl contact = new ContactImpl(10, "John");

	@Test
	public void testsGetId() {
		if (contact.getId() == 10) {
			valueActual = 1;
		}
		test();
	}

	@Test
	public void testsGetName() {
		valueExpected = "";
		valueActual = contact.getNotes();
		test();
		
		valueExpected = "John";
		valueActual = contact.getName();
		test();
	}

	@Test
	public void testsGetNotes() {
		valueExpected = "";
		valueActual = contact.getNotes();
		test();
	}
	
	@Test
	public void testsAddNotes() {
		contact.addNotes("hello");
		contact.addNotes(" world");
		valueExpected = "hello world";
		valueActual = contact.getNotes();
		test();
	}
}
