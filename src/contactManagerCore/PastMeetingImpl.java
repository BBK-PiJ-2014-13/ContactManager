package contactManagerCore;

import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting{
	private String notes;
	
	public PastMeetingImpl(int id, Calendar calendar, Set<Contact> contacts, String notes) {
		super(id, calendar, contacts);
		this.notes = notes;
	}

	public String getNotes() {
		return notes;
	}
	
	public String addNotes(String input) {
		notes += input + "\n";
		return notes;
	}

}
