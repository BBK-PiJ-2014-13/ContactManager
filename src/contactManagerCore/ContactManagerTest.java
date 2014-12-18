package contactManagerCore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedList;

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
	public void testsAddFutureMeeting() {
		valueExpected = 1;
		manager.addFutureMeeting(contacts, date);
		valueActual = manager.addFutureMeeting(contacts, date);
		test();
	}

	@Test
	public void testsGetPastMeeting() {
		valueExpected = new MeetingImpl(0, date, contacts);
		manager.futureMeetings.put(0, (PastMeeting) valueExpected);
		valueActual = manager.getPastMeeting(0);
		test();
	}

	@Test
	public void testsGetFutureMeeting() {
		valueExpected = new MeetingImpl(0, date, contacts);
		manager.futureMeetings.put(0, (FutureMeeting) valueExpected);
		valueActual = manager.getFutureMeeting(0);
		test();
	}

	@Test
	public void testsGetMeeting() {
		valueExpected = new MeetingImpl(0, date, contacts);
		manager.futureMeetings.put(0, (Meeting) valueExpected);
		valueActual = manager.getMeeting(0);
		test();
	}

	@Test
	public void testsGetFutureMeetingListContact() {
		valueExpected = new ArrayList<Contact>();
		valueActual = manager.getFutureMeetingList(contact);
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
		valueExpected = new ArrayList<PastMeeting>();
		valueActual = manager.getPastMeetingList(contact);
		test();
	}

	@Test
	public void testsAddNewPastMeeting() {
		manager.addNewPastMeeting(contacts, date, "notes1");
		manager.addNewPastMeeting(contacts, date, "notes2");
		valueExpected = "notes2";
		valueActual = manager.getPastMeeting(1).getNotes();
		test();
	}

	@Test
	public void testsAddMeetingNotes() {
		manager.addFutureMeeting(contacts, date);
		valueExpected = "notes";
		manager.addMeetingNotes(0, "notes");
		valueActual = ((PastMeetingImpl) manager.futureMeetings.get(0)).getNotes();
		test();
	}

}
