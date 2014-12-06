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
	ContactImpl contact = new ContactImpl(0, null);
	HashSet<Contact> contacts = new HashSet<Contact>();
	GregorianCalendar date = new GregorianCalendar();

	@Before
	public void buildUp() {
		manager = new ContactManagerImpl();
	}

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
		manager.meetings.put(0, (PastMeeting) valueExpected);
		valueActual = manager.getPastMeeting(0);
		test();
	}

	@Test
	public void testsGetFutureMeeting() {
		valueExpected = new MeetingImpl(0, date, contacts);
		manager.meetings.put(0, (FutureMeeting) valueExpected);
		valueActual = manager.getFutureMeeting(0);
		test();
	}

	@Test
	public void testsGetMeeting() {
		valueExpected = new MeetingImpl(0, date, contacts);
		manager.meetings.put(0, (Meeting) valueExpected);
		valueActual = manager.getMeeting(0);
		test();
	}

	@Test
	public void testsGetFutureMeetingList(Contact contact) {
		valueExpected = new ArrayList<Contact>();
		valueActual = manager.getFutureMeetingList(contact);
		test();
	}

	@Test
	public void testsGetFutureMeetingList(Calendar date) {
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
	

}
