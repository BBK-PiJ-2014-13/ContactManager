package contactManagerCore;

import java.util.Calendar;

import org.junit.Test;

public class MeetingTest extends BasicTest {
	Meeting meeting = new Meeting(10);

	@Test
	public void testsGetId() {
		valueExpected = 10;
		valueActual = meeting.getId();
		test();
	}
}
