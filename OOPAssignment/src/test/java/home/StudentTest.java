package home;

import static org.junit.Assert.*;

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
@PrepareForTest({BufferedReader.class, BufferedWriter.class, Student.class})
public class StudentTest {
	@Mock
	BufferedReader reader;
	
	@Mock
	BufferedWriter writer;

	@Spy
	Student student = new Student();
	
	@Mock
	StudentAction studentact;
	
		
	@Before
	public void setUp() throws Exception {
		PowerMockito.doNothing().when(student).write(Mockito.anyString(), (BufferedWriter)Mockito.anyObject());
		PowerMockito.whenNew(StudentAction.class).withNoArguments().thenReturn(studentact);
		PowerMockito.when(studentact.StudentChoices(Mockito.anyInt(),(BufferedWriter) Mockito.any(), (BufferedReader) Mockito.any())).thenReturn(true);
	}
	
	
	@Test
	public void testClerkAuthentication() {
		System.out.println("Testing student authentication \n");
		boolean noErrors = true;
		try {
			PowerMockito.when(reader.readLine()).thenReturn("123","1234");
			noErrors =student.StudentLogin(writer, reader);
		} catch (IOException e) {
			noErrors = false;
		} catch (SQLException e) {
			noErrors = false;
		}
		assertTrue(noErrors);
	}



}


