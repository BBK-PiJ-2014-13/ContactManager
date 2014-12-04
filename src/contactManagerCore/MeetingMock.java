package contactManagerCore;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

public class MeetingMock implements Meeting{
	public MeetingMock(int i, Calendar c) {
		
	}
	
	public int getId() {
		return 0;
	}
	
	public Calendar getDate() {
		return null;
	}

	public Set<Contact> getContacts() {
		return null;
	}
}
