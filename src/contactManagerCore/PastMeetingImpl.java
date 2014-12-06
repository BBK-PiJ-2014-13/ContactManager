package contactManagerCore;

import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting{
	public String notes;
	
	public PastMeetingImpl(int id, Calendar calendar, Set<Contact> contacts, String notes) {
		super(id, calendar, contacts);
		this.notes = notes;
	}

	public String getNotes() {
		return notes;
	}

}
