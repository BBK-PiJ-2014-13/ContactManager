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
	GregorianCalendar calendar;
	String notes;

	@Before
	public void buildUp() {
		manager = new ContactManagerImpl();
		contact = new ContactImpl(13, "Arnold");
		contacts = new HashSet<Contact>();
		calendar = new GregorianCalendar(2015, 1, 1);
		notes = "hello world";
	}

	@Test
	public void addFutureMeetingTest() {
		manager.addFutureMeeting(contacts, calendar);
		if (manager.addFutureMeeting(contacts, calendar) == 1) {
			valueActual = 1;
		}
		test(); // Tests for correct return value
		buildUp();

		try {
			manager.addFutureMeeting(contacts,
					new GregorianCalendar(2010, 1, 1));
		} catch (IllegalArgumentException e) {
			valueActual = 1;
		}
		test(); // Tests for exception when trying to add a past meeting
		buildUp();

		contact = new ContactImpl(0, "Arnold");
		contacts.add(contact);
		manager.addNewContact("Arnold", "actor");
		contacts.add(new ContactImpl(13, "John"));
		try {
			manager.addFutureMeeting(contacts, calendar);
		} catch (IllegalArgumentException e) {
			valueActual = 1;
		}
		test();
	}

	@Test
	public void getPastMeetingTest() {
		manager.addNewPastMeeting(contacts, calendar, "notes1");
		manager.addNewPastMeeting(contacts, calendar, "notes2");
		manager.addFutureMeeting(contacts, calendar);
		valueExpected = "notes2";
		valueActual = manager.getPastMeeting(1).getNotes();
		test();

		valueExpected = null;
		valueActual = manager.getPastMeeting(10);
		test();

		valueExpected = 0;
		valueActual = 1;
		try {
			manager.getPastMeeting(2);
		} catch (IllegalArgumentException e) {
			valueExpected = 1;
		}
		test();
	}

	public void getFutureMeetingTest() {
		// Is tested by addFutureMeetingTest()
	}

	@Test
	public void getMeeting() {
		manager.addNewPastMeeting(contacts, calendar, "notes1");
		manager.addFutureMeeting(contacts, calendar);
		valueExpected = 1;
		valueActual = 0;
		if (manager.getMeeting(1) != null) {
			valueActual = 1;
		}
		test();

		valueActual = 0;
		if (manager.getMeeting(10) == null) {
			valueActual = 1;
		}
		test();
	}

	@Test
	public void getFutureMeetingListTest_Contact() {
		if (manager.getFutureMeetingList(contact).size() == 0) {
			valueActual = 1;
		}
		test();
		buildUp();

		Contact contact1 = new ContactImpl(0, "John");
		contact1.addNotes(notes);
		contacts.add(contact1);
		manager.addNewContact("John", notes);

		manager.addFutureMeeting(contacts, new GregorianCalendar(2015, 1, 2));
		manager.addFutureMeeting(contacts, new GregorianCalendar(2015, 1, 1));
		manager.addFutureMeeting(contacts, new GregorianCalendar(2015, 1, 5));
		manager.addFutureMeeting(contacts, new GregorianCalendar(2015, 1, 4));
		manager.addNewPastMeeting(contacts, new GregorianCalendar(2010, 1, 1),
				notes);
		manager.addNewPastMeeting(contacts, new GregorianCalendar(2011, 1, 1),
				notes);

		if (manager.getFutureMeetingList(contact1).size() == 4) {
			valueActual = 1;
		}
		test(); // Test if the size of return Collection is right

		valueActual = 0;
		if (manager.getFutureMeetingList(contact1).get(2).getDate().DAY_OF_MONTH == 4) {
			valueActual = 1;
		}
		test(); // Test if the list is chronologically sorted
	}

	@Test
	public void getFutureMeetingListTest_Calendar() {
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
	public void getPastMeetingListTest() {
		contacts.add(contact);
		manager.addNewPastMeeting(contacts, calendar, notes);
		manager.addNewPastMeeting(new HashSet<Contact>(), calendar, notes);

		valueExpected = 1;
		valueActual = manager.getPastMeetingList(contact).size();
		test();
	}

	public void addNewPastMeetingTest() {
		// Is tested by getPastMeetingTest()
	}

	@Test
	public void addMeetingNotesTest() {
		manager.addNewPastMeeting(contacts, calendar, "hello");
		manager.addMeetingNotes(0, "\nworld");
		valueExpected = "hello\nworld";
		valueActual = manager.getPastMeeting(0).getNotes();
		test();
	}

	public void addNewContactTest() {
		// Tested by getContactsTest_Int()
	}

	@Test
	public void getContactsTest_Int() {
		manager.addNewContact("Jones", "manager");
		manager.addNewContact("Arnold", "actor");
		manager.addNewContact("Obama", "president");
		valueExpected = 2;
		valueActual = manager.getContacts(1, 2).toArray().length;
		test();

		valueExpected = 0;
		valueActual = 1;
		int[] array = { 0, 1, 4 };
		try {
			manager.getContacts(array);
		} catch (IllegalArgumentException e) {
			valueExpected = 1;
		}
		test();
	}

	@Test
	public void getContactsTest_String() {
		manager.addNewContact("Jones", "manager");
		manager.addNewContact("Arnold", "actor");
		manager.addNewContact("Obama", "president");
		valueExpected = 1;
		valueActual = manager.getContacts("Jones").toArray().length;
		test();

		valueExpected = 0;
		valueActual = 1;
		String emptyStr = null;
		try {
			manager.getContacts(emptyStr);
		} catch (NullPointerException e) {
			valueExpected = 1;
		}
		test();
	}

	@Test
	public void flushTest() {
		valueExpected = 1;
		valueActual = 0;
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
	public void testsHasAllContacts() {
		Set<Contact> set = new HashSet<Contact>();
		manager.addNewContact("Tom", "programmer");
		set.add(new ContactImpl(1, "John"));
		if (!manager.hasAllContacts(set)) {
			valueActual = 1;
		}
		test();
		valueActual = 0;
		
		manager.addNewContact("John", "director");

		if (manager.hasAllContacts(set)) {
			valueActual = 1;
		}
		test();
	}
}
