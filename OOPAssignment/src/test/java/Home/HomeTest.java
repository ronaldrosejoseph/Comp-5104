
package home;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


@RunWith(PowerMockRunner.class)
@PrepareForTest({BufferedReader.class, BufferedWriter.class, Home.class})
public class HomeTest {
	
	@Mock
	BufferedReader reader;
	
	@Mock
	BufferedWriter writer;

	@Spy
	Home obj = new Home();
	
	@Mock
	Clerk clerk;
	
	@Mock
	Student student;
	
	
	@Before
	public void setUp() throws Exception {
		PowerMockito.doNothing().when(obj).write(Mockito.anyString(), (BufferedWriter)Mockito.anyObject());
		PowerMockito.whenNew(Clerk.class).withNoArguments().thenReturn(clerk);
		PowerMockito.whenNew(Student.class).withNoArguments().thenReturn(student);
		PowerMockito.when(clerk.Validation((BufferedWriter) Mockito.any(), (BufferedReader) Mockito.any())).thenReturn(true);
		PowerMockito.when(student.StudentLogin(writer, reader)).thenReturn(true);
	}
	
	@Test
	public void testtimer() throws InterruptedException, IOException {
		System.out.println("Testing no of days for 20sec \n");
		Thread.sleep(20000);
		assertEquals(1,obj.getDays()); // 1 day is assumed as 20 seconds
	}
	

	
	@Test
	public void testClerkSelect() {
		System.out.println("Testing clerk selection \n");
		boolean noErrors = true;
		try {
			PowerMockito.when(reader.readLine()).thenReturn("1");
			obj.getData(writer, reader);
		} catch (IOException e) {
			noErrors = false;
		} catch (SQLException e) {
			noErrors = false;
		}
		assertTrue(noErrors);
	}
	
	@Test
	public void testStudentSelect() {
		System.out.println("Testing student selection \n");
		boolean noErrors = true;
		try {
			PowerMockito.when(reader.readLine()).thenReturn("2");
			obj.getData(writer, reader);
		} catch (IOException e) {
			noErrors = false;
		} catch (SQLException e) {
			noErrors = false;
		}
		assertTrue(noErrors);
	}
	
	
}

