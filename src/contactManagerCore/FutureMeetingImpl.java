package contactManagerCore;

import java.util.Calendar;
import java.util.Set;

public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting{

	public FutureMeetingImpl(int id, Calendar calendar, Set<Contact> contacts) {
		super(id, calendar, contacts);
	}
	
}
