package contactManagerCore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactManagerImpl implements ContactManager {
	private ArrayList<Meeting> meetingsList = new ArrayList<Meeting>();
	private ArrayList<Contact> contactsList = new ArrayList<Contact>();

	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		int isInPast = date.getTime().compareTo(new Date());
		boolean hasAllContacts = this.contactsList.containsAll(contacts);
		if (isInPast == -1) {
			throw new IllegalArgumentException();
		}

		if (!hasAllContacts) {
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
		Meeting meeting = new PastMeetingImpl(meetingsList.size(), date,
				contacts, text);
		meetingsList.add(meeting);
	}

	@Override
	public void addMeetingNotes(int id, String text) {
		for (int i = 0; i < meetingsList.size(); i++) {
			Meeting curElement = meetingsList.get(i);
			if (curElement instanceof PastMeeting && curElement.getId() == id) {
				((PastMeetingImpl) meetingsList.get(i)).addNotes(text);
			}
		}

	}

	@Override
	public void addNewContact(String name, String notes) {
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

	public boolean hasAllContacts(Set<Contact> set) {
		Iterator<Contact> iterator = set.iterator();
		while (iterator.hasNext()) {
			Contact curElem = iterator.next();
			boolean hasElement = false;
			for (int i = 0; i < contactsList.size(); i++) {
				boolean idIsSame = contactsList.get(i).getId() == curElem
						.getId();
				boolean nameIsSame = contactsList.get(i).getName() == curElem
						.getName();
				if (idIsSame && nameIsSame) {
					hasElement = true;
				}
				if (hasElement) {
					break;
				}
				if (i + 1 == contactsListS.size()) {
					if (!hasElement) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
