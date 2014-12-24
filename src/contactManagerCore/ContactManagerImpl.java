package contactManagerCore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactManagerImpl implements ContactManager {
	private ArrayList<Meeting> meetings = new ArrayList<Meeting>();
	private ArrayList<Contact> contacts = new ArrayList<Contact>();

	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		int isInPast = date.getTime().compareTo(new Date());
		boolean hasAllContacts = this.contacts.containsAll(contacts);
		if (isInPast == -1 || !hasAllContacts) {
			throw new IllegalArgumentException();
		}
		Meeting meeting = new FutureMeetingImpl(meetings.size(), date, contacts);
		meetings.add(meeting);
		return meeting.getId();
	}

	@Override
	public PastMeeting getPastMeeting(int id) {
		if (id >= meetings.size()) {
			return null;
		}
		if (meetings.get(id) instanceof PastMeeting) {
			return (PastMeeting) meetings.get(id);
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
		if (id >= meetings.size()) {
			return null;
		}
		return meetings.get(id);
	}

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		List<Meeting> resultList = new ArrayList<Meeting>();
		for (int i = 0; i < meetings.size(); i++) {
			if (meetings.get(i) instanceof FutureMeeting) {
				for (int j = 0; j < meetings.get(i).getContacts().size(); j++) {
					if (meetings.get(i).getContacts().contains(contact)) {
						resultList.add(meetings.get(i));
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
		for (int i = 0; i < meetings.size(); i++) {
			Meeting curMeeting = meetings.get(i);
			Calendar curElement = curMeeting.getDate();
			Date thisDate = new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH),
					date.get(Calendar.DATE)).getTime();
			Date targetDate = new GregorianCalendar(curElement.get(Calendar.YEAR), curElement.get(Calendar.MONTH),
					curElement.get(Calendar.DATE)).getTime();
			if (thisDate.equals(targetDate)) {
				resultList.add(curMeeting);
			}
		}
		return sortChronologically(resultList);
	}

	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		List<Meeting> startingList = new ArrayList<Meeting>();
		for (int i = 0; i < meetings.size(); i++) {
			if (meetings.get(i) instanceof PastMeeting) {
				for (int j = 0; j < meetings.get(i).getContacts().size(); j++) {
					if (meetings.get(i).getContacts().contains(contact)) {
						startingList.add(meetings.get(i));
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
		Meeting meeting = new PastMeetingImpl(meetings.size(), date, contacts,
				text);
		meetings.add(meeting);
	}

	@Override
	public void addMeetingNotes(int id, String text) {
		for (int i = 0; i < meetings.size(); i++) {
			Meeting curElement = meetings.get(i);
			if (curElement instanceof PastMeeting && curElement.getId() == id) {
				((PastMeetingImpl) meetings.get(i)).addNotes(text);
			}
		}

	}

	@Override
	public void addNewContact(String name, String notes) {
		contacts.add(new ContactImpl(contacts.size(), name));
		contacts.get(contacts.size() - 1).addNotes(notes);

	}

	@Override
	public Set<Contact> getContacts(int... ids) {
		HashSet<Contact> resultSet = new HashSet<Contact>();
		for (int i = 0; i < ids.length; i++) {
			if (ids[i] >= contacts.size()) {
				throw new IllegalArgumentException();
			}
			if (contacts.get(ids[i]) != null) {
				resultSet.add(contacts.get(ids[i]));
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
		for (int i = 0; i < contacts.size(); i++) {
			curElem = contacts.get(i);
			if (curElem.getName() == name) {
				resultSet.add(curElem);
			}
		}
		return resultSet;
	}

	@Override
	public void flush() {
		// Write to file
	}
	
	public void writeMeetings() {
		
	}
	
	public void writeContacts() {
		
	}

}
