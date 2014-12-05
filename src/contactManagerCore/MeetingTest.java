package contactManagerCore;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;

import org.junit.Test;

public class MeetingTest extends BasicTest {
	HashSet<Contact> contact = new HashSet<Contact>();
	GregorianCalendar calendar = new GregorianCalendar();
	MeetingImpl meeting = new MeetingImpl(10, calendar, contact);

	public void testsGetId() {
		valueExpected = 10;
		valueActual = meeting.getId();
		test();
	}
	
	public void testsGetDate() {
		valueExpected = calendar;
		valueActual = meeting.getDate();
		test();
	}
	
	public void testsGetContacts() {
		valueExpected = contact;
		valueActual = meeting.getContacts();
		test();
	}
}
