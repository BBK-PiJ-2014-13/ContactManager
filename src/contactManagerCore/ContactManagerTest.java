package contactManagerCore;

import java.util.GregorianCalendar;
import java.util.HashSet;

import org.junit.Test;

public class ContactManagerTest extends BasicTest {
	ContactManagerImpl manager = new ContactManagerImpl();
	HashSet<Contact> contacts = new HashSet<Contact>();
	GregorianCalendar date = new GregorianCalendar();

	@Test
	public void testsAddFutureMeeting() {
		manager.addFutureMeeting(contacts, date);
		valueExpected = true;
		if (manager.meetings.get(0) == null) {
			valueActual = false;
		} else {
			valueActual = true;
		}
		test();
	}
	
	@Test
	public void testsGetPastMeeting() {
		valueExpected = new MeetingImpl(0, date, contacts);
		manager.meetings.put(0, (Meeting) valueExpected);
		valueActual = manager.getPastMeeting(0);
		test();
	}
	
	@Test
	public void testsGetFutureMeeting() {
		valueExpected = new MeetingImpl(0, date, contacts);
		manager.meetings.put(0, (Meeting) valueExpected);
		valueActual = manager.getFutureMeeting(0);
		test();
	}
}
