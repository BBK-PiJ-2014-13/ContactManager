package contactManagerCore;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;

import org.junit.Test;

public class MeetingTest extends BasicTest {
	HashSet<Contact> sc = new HashSet<Contact>();
	GregorianCalendar gc = new GregorianCalendar();
	MeetingMock meeting = new MeetingMock(10, gc, sc);

	public void testsGetId() {
		valueExpected = 10;
		valueActual = meeting.getId();
		test();
	}
	
	public void testsGetDate() {
		valueExpected = gc;
		valueActual = meeting.getDate();
		test();
	}

	@Test
	public void testsGetContacts() {
		valueExpected = sc;
		valueActual = meeting.getContacts();
		test();
	}
}
