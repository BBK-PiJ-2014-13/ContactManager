package contactManagerCore;

import java.util.GregorianCalendar;
import java.util.HashSet;

import org.junit.Test;

public class MeetingTest extends BasicTest {
	HashSet<Contact> contact = new HashSet<Contact>();
	GregorianCalendar calendar = new GregorianCalendar();
	MeetingImpl meeting = new MeetingImpl(10, calendar, contact);

	@Test
	public void testsGetId() {
		valueExpected = 10;
		valueActual = meeting.getId();
		test();
	}
	
	@Test
	public void testsGetDate() {
		valueExpected = calendar;
		valueActual = meeting.getDate();
		test();
	}
	
	@Test
	public void testsGetContacts() {
		valueExpected = contact;
		valueActual = meeting.getContacts();
		test();
	}
}
