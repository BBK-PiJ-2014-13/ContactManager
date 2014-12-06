package contactManagerCore;

import java.util.GregorianCalendar;
import java.util.HashSet;

import org.junit.Test;

public class ContactManagerTest extends BasicTest {
	ContactManagerImpl manager = new ContactManagerImpl();
	HashSet<Contact> contacts = new HashSet<Contact>();
	GregorianCalendar date = new GregorianCalendar();

	@Test
	public void testAddFutureMeeting() {
		manager.addFutureMeeting(contacts, date);
		valueExpected = true;
		if (manager.meetings.get(0) == null) {
			valueActual = false;
		} else {
			valueActual = true;
		}
		test();
	}
}
