package contactManagerCore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class ContactManagerTest extends BasicTest {
	ContactManagerImpl manager;
	ContactImpl contact;
	HashSet<Contact> contacts;
	GregorianCalendar date;
	String notes;

	@Before
	public void buildUp() {
		manager = new ContactManagerImpl();
		contact = new ContactImpl(13, "Arnold");
		contacts = new HashSet<Contact>();
		date = new GregorianCalendar();
		notes = "hello world";
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
	public void testsGetFutureMeetingListContact() {
		contacts.add(contact);
		manager.addFutureMeeting(contacts, date);
		manager.addFutureMeeting(new HashSet<Contact>(), date);

		valueExpected = 1;
		valueActual = manager.getFutureMeetingList(contact).size();
		test();
	}

	@Test
	public void testsGetFutureMeetingListCalendar() {
		Calendar date1 = new GregorianCalendar(2015, 1, 5, 12, 00);
		Calendar date2 = new GregorianCalendar(2015, 1, 4, 12, 00);
		manager.addFutureMeeting(contacts, date1);
		manager.addFutureMeeting(contacts, date2);
		manager.addFutureMeeting(contacts, date1);

		valueExpected = 2;
		valueActual = manager.getFutureMeetingList(date1).size();
		test();
	}

	@Test
	public void testsSortChronologically() {
		Meeting meeting0 = new FutureMeetingImpl(0, new GregorianCalendar(2015,
				01, 05, 12, 00), contacts);
		Meeting meeting1 = new FutureMeetingImpl(1, new GregorianCalendar(2015,
				01, 04, 12, 00), contacts);
		Meeting meeting2 = new FutureMeetingImpl(2, new GregorianCalendar(2015,
				01, 05, 12, 10), contacts);
		ArrayList<Meeting> arrayList = new ArrayList<Meeting>();
		arrayList.add(meeting0);
		arrayList.add(meeting1);
		arrayList.add(meeting2);
		valueExpected = 0;
		valueActual = arrayList.get(0).getId();
		test();

		manager.sortChronologically(arrayList);
		valueExpected = 1;
		valueActual = arrayList.get(0).getId();
		test();

	}

	@Test
	public void testsGetPastMeetingList() {
		contacts.add(contact);
		manager.addNewPastMeeting(contacts, date, notes);
		manager.addNewPastMeeting(new HashSet<Contact>(), date, notes);

		valueExpected = 1;
		valueActual = manager.getPastMeetingList(contact).size();
		test();
	}

	@Test
	public void testsAddFutureMeeting() {
		test();
	}

	@Test
	public void testsGetPastMeeting() {
		test();
	}

	@Test
	public void testsGetFutureMeeting() {
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
	public void testsAddMeetingNotes() {
		manager.addNewPastMeeting(contacts, date, "hello");
		manager.addMeetingNotes(0, "\nworld");
		valueExpected = "hello\nworld";
		valueActual = manager.getPastMeeting(0).getNotes();
		test();
	}

	@Test
	public void testsAddNewContact() {
		test();

	}

	@Test
	public void testsGetContactsInt() {
		test();
	}

	@Test
	public void testsGetContactsString() {
		test();
	}

	@Test
	public void testsFlush() {
		test();
	}

}
