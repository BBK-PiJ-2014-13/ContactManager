package contactManagerCore;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

public class MeetingImpl implements Meeting{
	private int id;
	private Calendar calendar;
	private Set<Contact> contacts;
	
	public MeetingImpl(int i, Calendar cal, Set<Contact> con) {
		id = i;
		calendar = cal;
		contacts = con;
	}
	
	public int getId() {
		return id;
	}
	
	public Calendar getDate() {
		return null;
	}

	public Set<Contact> getContacts() {
		return null;
	}
}
