package contactManagerCore;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MeetingMock {
	public MeetingMock(int i) {
		
	}
	
	public int getId() {
		return 0;
	}
	
	public Calendar testsGetDate(Calendar c) {
		return new GregorianCalendar();
	}
}
