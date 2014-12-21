package contactManagerCore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ContactManagerImpl implements ContactManager {
	// TODO make field private
	public HashMap<Integer, Meeting> meetings = new HashMap<Integer, Meeting>();

	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		Meeting meeting = new FutureMeetingImpl(meetings.size(), date, contacts);
		meetings.put(meeting.getId(), meeting);
		return meeting.getId();
	}

	@Override
	public PastMeeting getPastMeeting(int id) {
		if (meetings.get(id) == null) {
			return null;
		} else if (meetings.get(id) instanceof PastMeeting) {
			return (PastMeeting) meetings.get(id);
		} else {
			return null;
		}

	}

	@Override
	public FutureMeeting getFutureMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meeting getMeeting(int id) {
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
			}} );
		return list;
	}
			    
	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date,
			String text) {
		Meeting meeting = new PastMeetingImpl(meetings.size(), date, contacts,
				text);
		meetings.put(meeting.getId(), meeting);
	}

	@Override
	public void addMeetingNotes(int id, String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addNewContact(String name, String notes) {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<Contact> getContacts(int... ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Contact> getContacts(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

}
