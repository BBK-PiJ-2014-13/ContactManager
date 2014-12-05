package contactManagerCore;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

public class MeetingImpl implements Meeting{
	private int id;
	private Calendar calendar;
	private Set<Contact> contacts;
	
	public MeetingImpl(int id, Calendar calendar, Set<Contact> contacts) {
		this.id = id;
		this.calendar = calendar;
		this.contacts = contacts;
	}
	
	public int getId() {
		return id;
	}
	
	public Calendar getDate() {
		return calendar;
	}

	public Set<Contact> getContacts() {
		return contacts;
	}
}
