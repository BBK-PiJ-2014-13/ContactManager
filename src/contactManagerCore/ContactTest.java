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
		if (contact.getNotes() == "") {
			valueActual = 1;
		}
		test();
		
		if (contact.getName() == "John") {
			valueActual = 1;
		}
		test();
	}

	@Test
	public void testsGetNotes() {
		if (contact.getNotes() == "") {
			valueActual = 1;
		}
		test();
	}
	
	@Test
	public void testsAddNotes() {
		contact.addNotes("hello");
		contact.addNotes(" world");
		if (contact.getNotes().equals("hello world")) {
			valueActual = 1;
		}
		test();
	}
}
