package contactManagerCore;

import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting{

	public PastMeetingImpl(int i, Calendar cal, Set<Contact> con, String not) {
		super(i, cal, con);
	}

	@Override
	public String getNotes() {
		// TODO Auto-generated method stub
		return null;
	}

}
