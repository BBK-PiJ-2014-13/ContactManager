package contactManagerCore;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ContactManagerImpl implements ContactManager {
	private ArrayList<Meeting> meetingsList = new ArrayList<Meeting>();
	private ArrayList<Contact> contactsList = new ArrayList<Contact>();
	
	public ContactManagerImpl() {
		importLists();
	}

	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		int isInPast = date.getTime().compareTo(new Date());
		boolean hasOneOrMore = contacts.size() >= 1;
		if (isInPast == -1) {
			throw new IllegalArgumentException();
		}
		if (!hasOneOrMore) {
			throw new IllegalArgumentException();
		}
		if (!hasAllContacts(contacts)) {
			throw new IllegalArgumentException();
		}
		Meeting meeting = new FutureMeetingImpl(meetingsList.size(), date,
				contacts);
		meetingsList.add(meeting);
		return meeting.getId();
	}

	@Override
	public PastMeeting getPastMeeting(int id) {
		if (id >= meetingsList.size()) {
			return null;
		}
		if (meetingsList.get(id) instanceof PastMeeting) {
			return (PastMeeting) meetingsList.get(id);
		} else {
			throw new IllegalArgumentException();
		}

	}

	@Override
	public FutureMeeting getFutureMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meeting getMeeting(int id) {
		if (id >= meetingsList.size()) {
			return null;
		}
		return meetingsList.get(id);
	}

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		hasThisContact(contact);

		List<Meeting> resultList = new ArrayList<Meeting>();
		for (int i = 0; i < meetingsList.size(); i++) {
			if (meetingsList.get(i) instanceof FutureMeeting) {
				for (int j = 0; j < meetingsList.get(i).getContacts().size(); j++) {
					if (meetingsList.get(i).getContacts().contains(contact)) {
						resultList.add(meetingsList.get(i));
						break;
					}
				}
			}
		}

		resultList = sortChronologically(resultList);
		return resultList;
	}

	public List<Meeting> sortChronologically(List<Meeting> list) {
		Collections.sort(list, new Comparator<Meeting>() {

			@Override
			public int compare(Meeting m1, Meeting m2) {
				return m1.getDate().getTime().compareTo(m2.getDate().getTime());
			}
		});
		return list;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		List<Meeting> resultList = new ArrayList<Meeting>();
		for (int i = 0; i < meetingsList.size(); i++) {
			Meeting curMeeting = meetingsList.get(i);
			Calendar curElement = curMeeting.getDate();
			Date thisDate = new GregorianCalendar(date.get(Calendar.YEAR),
					date.get(Calendar.MONTH), date.get(Calendar.DATE))
					.getTime();
			Date targetDate = new GregorianCalendar(
					curElement.get(Calendar.YEAR),
					curElement.get(Calendar.MONTH),
					curElement.get(Calendar.DATE)).getTime();
			if (thisDate.equals(targetDate)) {
				resultList.add(curMeeting);
			}
		}
		return sortChronologically(resultList);
	}

	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		hasThisContact(contact);

		List<Meeting> startingList = new ArrayList<Meeting>();
		for (int i = 0; i < meetingsList.size(); i++) {
			if (meetingsList.get(i) instanceof PastMeeting) {
				for (int j = 0; j < meetingsList.get(i).getContacts().size(); j++) {
					if (meetingsList.get(i).getContacts().contains(contact)) {
						startingList.add(meetingsList.get(i));
						break;
					}
				}
			}
		}

		startingList = sortChronologically(startingList);
		List<PastMeeting> resultList = new ArrayList<PastMeeting>();
		for (int i = 0; i < startingList.size(); i++) {
			resultList.add((PastMeeting) startingList.get(i));
		}
		return resultList;
	}

	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date,
			String text) {
		if (contacts == null || date == null || text == null) {
			throw new NullPointerException();
		}

		if (contacts.size() == 0) {
			throw new IllegalArgumentException();
		}

		if (!hasAllContacts(contacts)) {
			throw new IllegalArgumentException();
		}

		Meeting meeting = new PastMeetingImpl(meetingsList.size(), date,
				contacts, text);
		meetingsList.add(meeting);
	}

	@Override
	public void addMeetingNotes(int id, String text) {
		boolean hasThisId = !(id >= meetingsList.size());
		if (!hasThisId) {
			throw new IllegalArgumentException();
		}

		boolean isPast = meetingsList.get(id) instanceof PastMeeting;
		if (!isPast) {
			throw new IllegalStateException();
		}

		if (text == null) {
			throw new NullPointerException();
		}

		((PastMeetingImpl) meetingsList.get(id)).addNotes(text);
	}

	@Override
	public void addNewContact(String name, String notes) {
		if (name == null || notes == null) {
			throw new NullPointerException();
		}

		contactsList.add(new ContactImpl(contactsList.size(), name));
		contactsList.get(contactsList.size() - 1).addNotes(notes);

	}

	@Override
	public Set<Contact> getContacts(int... ids) {
		HashSet<Contact> resultSet = new HashSet<Contact>();
		for (int i = 0; i < ids.length; i++) {
			if (ids[i] >= contactsList.size()) {
				throw new IllegalArgumentException();
			}
			if (contactsList.get(ids[i]) != null) {
				resultSet.add(contactsList.get(ids[i]));
			}
		}
		return resultSet;
	}

	@Override
	public Set<Contact> getContacts(String name) {
		HashSet<Contact> resultSet = new HashSet<Contact>();
		if (name == null) {
			throw new NullPointerException();
		}
		Contact curElem;
		for (int i = 0; i < contactsList.size(); i++) {
			curElem = contactsList.get(i);
			if (curElem.getName().contains(name)) {
				resultSet.add(curElem);
			}
		}
		return resultSet;
	}

	@Override
	public void flush() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// Iterates 2 times, one for meetings and one for contacts
			boolean writeMeetings = true;
			for (int iterations = 0; iterations < 2; iterations++) {

				// root element
				Document doc = docBuilder.newDocument();
				Element rootElement;
				if (writeMeetings) {
					rootElement = doc.createElement("meetings");
				} else {
					rootElement = doc.createElement("contacts");
				}

				doc.appendChild(rootElement);

				if (writeMeetings) {
					// Meetings elements
					for (int i = 0; i < meetingsList.size(); i++) {
						Meeting curEl = meetingsList.get(i);
						Element meeting = doc.createElement("meeting");
						meeting.setAttribute("id", Integer.toString(i));
						rootElement.appendChild(meeting);

						Node calendarNode = doc.createElement("date");
						calendarNode.appendChild(doc.createTextNode(curEl
								.getDate().getTime().toString()));
						meeting.appendChild(calendarNode);
					}
				} else {
					// Contact elements
					for (int i = 0; i < contactsList.size(); i++) {
						Contact curEl = contactsList.get(i);
						Element contact = doc.createElement("contact");
						contact.setAttribute("id", Integer.toString(i));
						rootElement.appendChild(contact);

						Node nameNode = doc.createElement("name");
						nameNode.appendChild(doc.createTextNode(curEl.getName()));
						contact.appendChild(nameNode);

						Node notesNode = doc.createElement("notes");
						notesNode.appendChild(doc.createTextNode(curEl
								.getNotes()));
						contact.appendChild(notesNode);
					}
				}

				// Write the content into xml files
				TransformerFactory transformerFactory = TransformerFactory
						.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty(
						"{http://xml.apache.org/xslt}indent-amount", "2");
				DOMSource source = new DOMSource(doc);
				StreamResult result;
				if (writeMeetings) {
					result = new StreamResult(new File("meetings.xml"));
				} else {
					result = new StreamResult(new File("contacts.xml"));
				}
				transformer.transform(source, result);
				writeMeetings = !writeMeetings;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void importLists() {
		
	}
	
	public boolean hasAllContacts(Set<Contact> set) {
		Iterator<Contact> iterator = set.iterator();
		while (iterator.hasNext()) {
			Contact curSetElem = iterator.next();
			if (curSetElem.getId() >= contactsList.size()) {
				return false;
			}
			Contact curArrayElem = contactsList.get(curSetElem.getId());
			boolean nameIsSame = curArrayElem.getName().equals(
					curSetElem.getName());
			if (!nameIsSame) {
				return false;
			}
		}
		return true;
	}

	public boolean hasThisContact(Contact contact) {
		boolean hasThisID = !(contact.getId() >= contactsList.size());
		if (!hasThisID) {
			throw new IllegalArgumentException();
		}
		boolean hasThisContact = contact.getName().equals(
				contactsList.get(contact.getId()).getName());
		if (!hasThisContact) {
			throw new IllegalArgumentException();
		}
		return true;
	}
}
