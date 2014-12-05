package contactManagerCore;

import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting{
	String notes;
	
	public PastMeetingImpl(int i, Calendar cal, Set<Contact> con, String notes) {
		super(i, cal, con);
		this.notes = notes;
	}

	public String getNotes() {
		return null;
	}

}
