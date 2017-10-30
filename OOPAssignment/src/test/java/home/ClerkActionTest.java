package home;

import static org.junit.Assert.*;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import database.CourseDataAdd;
import database.CourseDataDelete;
import database.DbConnect;
import database.StudentDataAdd;
import database.StudentDataDelete;


	@RunWith(PowerMockRunner.class)
	@PrepareForTest({BufferedReader.class, BufferedWriter.class, ClerkAction.class})
	public class ClerkActionTest {
		
		@Mock
		BufferedReader reader;
		
		@Mock
		BufferedWriter writer;

		@Spy
		ClerkAction obj = new ClerkAction();
		
		@Mock
		Clerk clerk;
		
		@Mock
		CourseDataAdd caddobj;
		
		@Mock
		CourseDataDelete cdel;
		
		@Mock
		StudentDataAdd sdadd;
		
		@Mock
		StudentDataDelete sddel;
		
		@Mock
		Home home;
		
		@Mock
		DbConnect dbobj;
		
		@Before
		public void setUp() throws Exception {
			PowerMockito.doNothing().when(obj).write(Mockito.anyString(), (BufferedWriter)Mockito.anyObject());
			PowerMockito.whenNew(CourseDataAdd.class).withNoArguments().thenReturn(caddobj);
			PowerMockito.whenNew(CourseDataDelete.class).withNoArguments().thenReturn(cdel);
			PowerMockito.whenNew(StudentDataAdd.class).withNoArguments().thenReturn(sdadd);
			PowerMockito.whenNew(StudentDataDelete.class).withNoArguments().thenReturn(sddel);
//			PowerMockito.whenNew(Home.class).withNoArguments().thenReturn(home);
			PowerMockito.doNothing().when(home).getData(writer, reader);
		}
		
		

		
		@Test
		public void testClerkSelect1() {
			System.out.println("Testing CourseDataAdd selection \n");
			boolean noErrors = true;
			try {
				PowerMockito.when(reader.readLine()).thenReturn("1");
				noErrors = obj.clientChoices(writer, reader);
			} catch (IOException e) {
				noErrors = false;
			} catch (SQLException e) {
				noErrors = false;
			}
			assertTrue(noErrors);
		}
		
		@Test
		public void testClerkSelect2() {
			System.out.println("Testing CourseDataDelete selection \n");
			boolean noErrors = true;
			try {
				PowerMockito.when(reader.readLine()).thenReturn("2");
				noErrors = obj.clientChoices(writer, reader);
			} catch (IOException e) {
				noErrors = false;
			} catch (SQLException e) {
				noErrors = false;
			}
			assertTrue(noErrors);
		}
		
		@Test
		public void testClerkSelect3() {
			System.out.println("Testing StudentDataAdd selection \n");
			boolean noErrors = true;
			try {
				PowerMockito.when(reader.readLine()).thenReturn("3");
				noErrors = obj.clientChoices(writer, reader);
			} catch (IOException e) {
				noErrors = false;
			} catch (SQLException e) {
				noErrors = false;
			}
			assertTrue(noErrors);
		}
		
		@Test
		public void testClerkSelect4() {
			System.out.println("Testing StudentDataDelete selection \n");
			boolean noErrors = true;
			try {
				PowerMockito.when(reader.readLine()).thenReturn("4");
				noErrors = obj.clientChoices(writer, reader);
			} catch (IOException e) {
				noErrors = false;
			} catch (SQLException e) {
				noErrors = false;
			}
			assertTrue(noErrors);
		}
		

		@Test
		public void testClerkSelect5() {
			System.out.println("Testing Home selection \n");
			boolean noErrors = true;
			try {
				PowerMockito.when(reader.readLine()).thenReturn("4");
				noErrors = obj.clientChoices(writer, reader);
			} catch (IOException e) {
				noErrors = false;
			} catch (SQLException e) {
				noErrors = false;
			}
			assertTrue(noErrors);
		}
		


}
