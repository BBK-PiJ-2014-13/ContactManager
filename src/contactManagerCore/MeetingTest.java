package contactManagerCore;

import java.util.Calendar;

import org.junit.Test;

public class MeetingTest extends BasicTest {
	MeetingMock meeting = new MeetingMock(10);

	@Test
	public void testsGetId() {
		valueExpected = 10;
		valueActual = meeting.getId();
		test();
	}
}
