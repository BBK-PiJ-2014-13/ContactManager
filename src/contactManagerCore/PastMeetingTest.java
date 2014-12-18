package contactManagerCore;

import java.util.GregorianCalendar;
import java.util.HashSet;

import org.junit.Test;

public class PastMeetingTest extends BasicTest{
	HashSet<Contact> contact = new HashSet<Contact>();
	GregorianCalendar calendar = new GregorianCalendar();
	String notes = "notes";
	PastMeetingImpl meeting = new PastMeetingImpl(10, calendar, contact, notes);
	
	@Test
	public void testsGetNotes() {
		valueExpected = notes;
		valueActual = meeting.getNotes();
		test();
	}
}
