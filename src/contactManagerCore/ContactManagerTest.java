package contactManagerCore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ContactManagerTest extends BasicTest {
	ContactManagerImpl manager;
	ContactImpl contact;
	HashSet<Contact> contacts;
	GregorianCalendar date;

	@Before
	public void buildUp() {
		manager = new ContactManagerImpl();
		contact = new ContactImpl(13, null);
		contacts = new HashSet<Contact>();
		contacts.add(contact);
		date = new GregorianCalendar();
	}

	@Test
	public void testsAddAndGetFutureMeeting() {
		valueExpected = 1;
		manager.addFutureMeeting(contacts, date);
		valueActual = manager.addFutureMeeting(contacts, date);
		test();
	}

	@Test
	public void testsAddAndGetPastMeeting() {
		manager.addNewPastMeeting(contacts, date, "notes1");
		manager.addNewPastMeeting(contacts, date, "notes2");
		valueExpected = "notes2";
		valueActual = manager.getPastMeeting(1).getNotes();
		test();
		
		valueExpected = null;
		valueActual = manager.getPastMeeting(2);
		test();
	}


	@Test
	public void testsGetMeeting() {
		manager.addNewPastMeeting(contacts, date, "notes1");
		manager.addFutureMeeting(contacts, date);
		valueExpected = 1;
		valueActual = manager.getMeeting(1).getId();
		test();

		valueExpected = null;
		valueActual = manager.getMeeting(2);
		test();
	}

	@Test
	public void testsGetFutureMeetingListContact() {
		contacts.add(contact);
		manager.getMeeting(manager.addFutureMeeting(contacts, date));
		manager.addFutureMeeting(new HashSet<Contact>(), date);
		Meeting meetingReturn = manager.getMeeting(manager.addFutureMeeting(contacts, date));
		manager.getFutureMeetingList(contact);
		valueExpected = meetingReturn;
		valueActual = manager.getPastMeetingList(contact).get(1);
		test();
	}

	@Test
	public void testsGetFutureMeetingListCalendar() {
		valueExpected = new ArrayList<Contact>();
		valueActual = manager.getFutureMeetingList(date);
		test();
	}

	@Test
	public void testsGetPastMeetingList() {
		contacts.add(contact);
		manager.getMeeting(manager.addFutureMeeting(contacts, date));
		manager.addFutureMeeting(new HashSet<Contact>(), date);
		Meeting meetingReturn = manager.getMeeting(manager.addFutureMeeting(contacts, date));
		manager.getFutureMeetingList(contact);
		valueExpected = meetingReturn;
		valueActual = manager.getPastMeetingList(contact).get(1);
		test();
	}

	@Test
	public void testsAddMeetingNotes() {
		manager.addFutureMeeting(contacts, date);
		valueExpected = "notes";
		manager.addMeetingNotes(0, "notes");
		valueActual = ((PastMeetingImpl) manager.meetings.get(0)).getNotes();
		test();
	}

	public void testsSortChronologically() {
		List<Meeting> list = new ArrayList<Meeting>();
		Calendar date1 = new GregorianCalendar(); 
		test();
	}
}
