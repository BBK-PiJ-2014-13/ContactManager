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
	valueExpected = manager.getFutureMeeting(0);
	}
}
