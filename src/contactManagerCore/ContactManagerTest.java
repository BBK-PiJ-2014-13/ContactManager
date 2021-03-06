package contactManagerCore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ContactManagerTest extends BasicTest {
	ContactManagerImpl manager;
	ContactImpl contact;
	HashSet<Contact> contacts;
	GregorianCalendar futureCalendar;
	String notes;

	@Before
	public void buildUp() {
		File contactsXML = new File("contacts.xml");
		File meetingsXML = new File("meetings.xml");
		contactsXML.delete();
		meetingsXML.delete();
		manager = new ContactManagerImpl();
		contact = new ContactImpl(13, "Arnold");
		contacts = new HashSet<Contact>();
		futureCalendar = new GregorianCalendar(2040, 1, 1);
		notes = "hello world";
	}
	
	@After
	public void cleanUp() {
		File contactsXML = new File("contacts.xml");
		File meetingsXML = new File("meetings.xml");
		contactsXML.delete();
		meetingsXML.delete();
	}

	@Test
	public void addFutureMeetingTest() {
		contacts.add(new ContactImpl(0, "John"));
		manager.addNewContact("John", "director");
		manager.addFutureMeeting(contacts, new GregorianCalendar(2040, 1, 1));
		int meetingID = manager.addFutureMeeting(contacts, new GregorianCalendar(2020, 1, 1));
		manager.addFutureMeeting(contacts, new GregorianCalendar(2030, 1, 1));
		valueExpected = 2020;
		valueActual = manager.getFutureMeeting(meetingID).getDate().get(Calendar.YEAR);
		test(); // if adds a meeting correctly
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
			manager.addFutureMeeting(contacts, futureCalendar);
		} catch (IllegalArgumentException e) {
			valueActual = 1;
		}
		test(); // Tests for exception when trying to add a meeting with an
				// unknown contact
		buildUp();

		try {
			manager.addFutureMeeting(contacts, futureCalendar);
		} catch (IllegalArgumentException e) {
			valueActual = 1;
		}
		test(); // Tests for exception when trying to add a meeting without any
				// contacts
	}

	@Test
	public void getPastMeetingTest() {
		contacts.add(new ContactImpl(0, "John"));
		manager.addNewContact("John", "director");
		manager.addNewPastMeeting(contacts, futureCalendar, "notes1");
		manager.addNewPastMeeting(contacts, futureCalendar, "notes2");
		manager.addFutureMeeting(contacts, futureCalendar);
		if (manager.getPastMeeting(1).getNotes().equals("notes2")) {
			valueActual = 1;
		}
		test(); // Test if the method returns correct object

		if (manager.getPastMeeting(10) == null) {
			valueActual = 1;
		}
		test(); // Tests if the method returns null when asked for a
				// non-existent meeting

		try {
			manager.getPastMeeting(2);
		} catch (IllegalArgumentException e) {
			valueActual = 1;
		}
		test(); // Tests exception when a meeting with that id happens in the
				// future
	}

	@Test
	public void getFutureMeetingTest() {
		contact = new ContactImpl(0, "John");
		contacts.add(contact);
		manager.addNewContact("John", "director");
		manager.addFutureMeeting(contacts, new GregorianCalendar(2040, 1, 1));
		manager.addFutureMeeting(contacts, new GregorianCalendar(2030, 1, 1));
		manager.addFutureMeeting(contacts, new GregorianCalendar(2020, 1, 1));
		valueExpected = 2030;
		valueActual = manager.getFutureMeeting(1).getDate().get(Calendar.YEAR);
		test(); // if returns correct future meeting
		
		valueExpected = null;
		valueActual = manager.getFutureMeeting(10);
		test(); // if returns null when there is no such meeting
	}

	@Test
	public void getMeeting() {
		contacts.add(new ContactImpl(0, "John"));
		manager.addNewContact("John", "director");
		manager.addNewPastMeeting(contacts, futureCalendar, "notes1");
		manager.addFutureMeeting(contacts, futureCalendar);
		if (manager.getMeeting(1).getId() == 1) {
			valueActual = 1;
		}
		test(); // Tests if returns correct meeting

		if (manager.getMeeting(10) == null) {
			valueActual = 1;
		}
		test(); // Tests if returns null when requested non-existent meeting
	}

	@Test
	public void getFutureMeetingListTest_Contact() {
		manager.addNewContact("John", "director");
		contacts.add(new ContactImpl(0, "John"));
		manager.addFutureMeeting(contacts, futureCalendar);
		contacts = new HashSet<Contact>();
		manager.addNewContact("Tom", "manager");
		if (manager.getFutureMeetingList(new ContactImpl(1, "Tom")).size() == 0) {
			valueActual = 1;
		}
		test(); // Test if returns empty list if there are no meetings
				// scheduled with this contact
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
		test(); // Test if returns sorted list with contacts no duplicates
		buildUp();

		manager.addNewContact("John", "director");
		contacts.add(new ContactImpl(0, "John"));
		manager.addFutureMeeting(contacts, futureCalendar);
		contacts = new HashSet<Contact>();
		try {
			manager.getFutureMeetingList(new ContactImpl(1, "Tom"));
		} catch (IllegalArgumentException e) {
			valueActual = 1;
		}
		test(); // Test if throws exception if contact does not exist
	}

	@Test
	public void getFutureMeetingListTest_Calendar() {
		Calendar date1 = new GregorianCalendar(2015, 1, 5, 12, 00);
		Calendar date2 = new GregorianCalendar(2015, 1, 4, 12, 00);
		Calendar date3 = new GregorianCalendar(2015, 1, 12, 12, 00);
		contacts.add(new ContactImpl(0, "John"));
		manager.addNewContact("John", "diretor");
		manager.addFutureMeeting(contacts, date1);
		manager.addFutureMeeting(contacts, date2);
		manager.addFutureMeeting(contacts, date1);

		if (manager.getFutureMeetingList(date1).size() == 2) {
			valueActual = 1;
		}
		test(); // Test if returns list with meeting on that date with no
				// duplicates

		manager.addNewContact("Tom", "manager");
		contacts = new HashSet<Contact>();
		contacts.add(new ContactImpl(1, "Tom"));
		if (manager.getFutureMeetingList(date3).size() == 0) {
			valueActual = 1;
		}
		test(); // Test if returns empty list if there are no meetings with this
				// contact
	}

	@Test
	public void getPastMeetingListTest() {
		contacts.add(new ContactImpl(0, "John"));
		manager.addNewContact("John", "director");
		manager.addNewPastMeeting(contacts, futureCalendar, notes);
		manager.addNewPastMeeting(contacts, futureCalendar, notes);
		manager.addNewPastMeeting(contacts, futureCalendar, notes);
		contacts = new HashSet<Contact>();
		contact = new ContactImpl(1, "Tom");
		contacts.add(contact);
		manager.addNewContact("Tom", "manager");
		manager.addNewPastMeeting(contacts, futureCalendar, notes);
		manager.addNewPastMeeting(contacts, futureCalendar, notes);

		if (manager.getPastMeetingList(contact).size() == 2) {
			valueActual = 1;
		}
		test(); // If returns list of past meetings with this contact
		buildUp();

		contact = new ContactImpl(0, "John");
		manager.addNewContact("John", "director");
		if (manager.getPastMeetingList(contact).size() == 0) {
			valueActual = 1;
		}

		test(); // if returns empty list when there were no meetings with this
				// contact
		buildUp();

		try {
			manager.getPastMeetingList(contact);
		} catch (IllegalArgumentException e) {
			valueActual = 1;
		}
		test(); // if throws exception when this contact doesn't exist
	}

	@Test
	public void addNewPastMeetingTest() {
		try {
			manager.addNewPastMeeting(contacts, futureCalendar, notes);
		} catch (IllegalArgumentException e) {
			valueActual = 1;
		}
		test(); // if throws exception if the list of contacts is empty

		manager.addNewContact("John", "director");
		contacts.add(new ContactImpl(0, "John"));
		contacts.add(new ContactImpl(1, "Tom"));
		try {
			manager.addNewPastMeeting(contacts, futureCalendar, notes);
		} catch (IllegalArgumentException e) {
			valueActual = 1;
		}
		test(); // if throws exception if any of the contacts does not exist

		contacts = null;
		try {
			manager.addNewPastMeeting(contacts, futureCalendar, notes);
		} catch (NullPointerException e) {
			valueActual = 1;
		}
		test(); // if throws exception if any of the arguments is null
	}

	@Test
	public void addMeetingNotesTest() {
		contacts.add(new ContactImpl(0, "John"));
		manager.addNewContact("John", "director");
		manager.addNewPastMeeting(contacts, futureCalendar, "hello");
		manager.addMeetingNotes(0, "\nworld");
		if (manager.getPastMeeting(0).getNotes().equals("hello\nworld")) {
			valueActual = 1;
		}
		test(); // if adds notes

		try {
			manager.addMeetingNotes(1, "\nJava");
		} catch (IllegalArgumentException e) {
			valueActual = 1;
		}
		test(); // if throws IllegalArgumentException if the meeting does not
				// exist

		manager.addFutureMeeting(contacts, new GregorianCalendar(2016, 1, 1));
		try {
			manager.addMeetingNotes(1, "\nJava");
		} catch (IllegalStateException e) {
			valueActual = 1;
		}
		test(); // if throws IllegalStateException if the meeting is set for a
				// date in the future

		try {
			manager.addMeetingNotes(0, null);
		} catch (NullPointerException e) {
			valueActual = 1;
		}
		test(); // if throws NullPointerException if the notes are null
	}

	@Test
	public void addNewContactTest() {
		try {
			manager.addNewContact(null, "director");
		} catch (NullPointerException e) {
			valueActual = 1;
		}
		test(); // if throws NullPointerException if the name is null

		try {
			manager.addNewContact("John", null);
		} catch (NullPointerException e) {
			valueActual = 1;
		}
		test(); // if throws NullPointerException if the notes are null
	}

	@Test
	public void getContactsTest_Int() {
		manager.addNewContact("Jones", "manager");
		manager.addNewContact("Arnold", "actor");
		manager.addNewContact("Obama", "president");
		if (manager.getContacts(1, 2).size() == 2) {
			valueActual = 1;
		}
		test(); // if returns a list containing the contacts that correspond to
				// the IDs

		int[] array = { 0, 1, 4 };
		valueExpected = new IllegalArgumentException().getClass()
				.getSimpleName();
		try {
			manager.getContacts(array);
		} catch (IllegalArgumentException e) {
			valueActual = e.getClass().getSimpleName();
		}
		test();
	}

	@Test
	public void getContactsTest_String() {
		manager.addNewContact("Jones", "manager");
		manager.addNewContact("Arnold", "actor");
		manager.addNewContact("John Smith", "president");
		if (manager.getContacts("Jo").toArray().length == 2) {
			valueActual = 1;
		}
		test(); // if return a list with the contacts whose name contains that
				// string

		String emptyStr = null;
		try {
			manager.getContacts(emptyStr);
		} catch (NullPointerException e) {
			valueActual = 1;
		}
		test(); // if throws NullPointerException if the parameter is null
	}

	@Test
	public void flushTest() {
		manager.addNewContact("John", "director");
		manager.addNewContact("Tom", "manager");
		manager.addNewContact("Larry", "programmer");
		Contact contactTest1 = new ContactImpl(0, "John");
		contactTest1.addNotes("director");
		Contact contactTest2 = new ContactImpl(1, "Tom");
		contactTest2.addNotes("manager");
		contacts.add(contactTest1);
		contacts.add(contactTest2);
		manager.addFutureMeeting(contacts, futureCalendar);
		manager.addNewPastMeeting(contacts, new GregorianCalendar(2014, 1, 1),
				notes);
		manager.flush();

		// Tests meetings
		try {
			File xmlFile = new File("meetings.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);

			NodeList listOfMeetings = doc.getElementsByTagName("meeting");
			valueExpected = 2;
			valueActual = listOfMeetings.getLength();
		} catch (Exception e) {
			valueActual = null;
		}
		test();

		// Tests contacts
		try {
			File xmlFile = new File("contacts.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);

			NodeList listOfContacts = doc.getElementsByTagName("contact");
			valueExpected = 3;
			valueActual = listOfContacts.getLength();
			test();

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void importListsTest() {
		manager.addNewContact("John", "director");
		manager.addNewContact("Tom", "manager");
		Contact contactTest1 = new ContactImpl(0, "John");
		contactTest1.addNotes("director");
		contacts.add(contactTest1);
		manager.addFutureMeeting(contacts, futureCalendar);
		manager.addNewPastMeeting(contacts, new GregorianCalendar(2010, 1, 1),
				notes);
		manager.flush();
		manager = new ContactManagerImpl();

		Contact contactTest = (Contact) manager.getContacts("Tom").toArray()[0];
		if (contactTest.getNotes().equals("manager")) {
			valueActual = 1;
		}
		test(); // Test if imported contacts correctly

		if (manager.getFutureMeeting(0).getDate().equals(futureCalendar)) {
			valueActual = 1;
		}
		test(); // Test if imported meetings correctly
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
		if (arrayList.get(0).getId() == 0) {
			valueActual = 1;
		}
		test();

		manager.sortChronologically(arrayList);
		if (arrayList.get(0).getId() == 1) {
			valueActual = 1;
		}
		test();
	}

	@Test
	public void testsHasAllContacts() {
		Set<Contact> set = new HashSet<Contact>();
		manager.addNewContact("Tom", "programmer");
		set.add(new ContactImpl(0, "John"));
		if (!manager.hasAllContacts(set)) {
			valueActual = 1;
		}
		test(); // Tests if the method returns false when it doesn't have a
				// contact
		buildUp();

		set = new HashSet<Contact>();
		set.add(new ContactImpl(1, "John"));
		manager.addNewContact("Tom", "manager");
		manager.addNewContact("John", "manager");

		if (manager.hasAllContacts(set)) {
			valueActual = 1;
		}
		test(); // Tests if the method returns true when it has all contacts
	}

	@Test
	public void addToListTest() {
		Contact contactTest1 = new ContactImpl(10, "John");
		valueExpected = "John";
		valueActual = ((Contact) manager.addToList(10, contactTest1)).getName();
		test(); // if element is in correct position inside of array
	}
}
