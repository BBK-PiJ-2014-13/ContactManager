package contactManagerCore;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MeetingMock {
	public MeetingMock(int i, Calendar c) {
		
	}
	
	public int getId() {
		return 0;
	}
	
	public Calendar GetDate(Calendar c) {
		return new GregorianCalendar();
	}
}
