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
import database.DbConnect;
import database.StudentCourseDataDelete;
import database.StudentCourseEnrol;


	@RunWith(PowerMockRunner.class)
	@PrepareForTest({BufferedReader.class, BufferedWriter.class, StudentAction.class})
	public class StudentActionTest {
		
		@Mock
		BufferedReader reader;
		
		@Mock
		BufferedWriter writer;

		@Spy
		StudentAction obj = new StudentAction();
		
		@Mock
		StudentCourseEnrol senrol;
		
		@Mock
		StudentCourseDataDelete sdel;
		
		@Mock
		Home home;
		
		@Mock
		StudentAction stdact;
		
		@Mock
		DbConnect db;
		
		@Before
		public void setUp() throws Exception {
			PowerMockito.doNothing().when(obj).write(Mockito.anyString(), (BufferedWriter)Mockito.anyObject());
			PowerMockito.whenNew(StudentCourseEnrol.class).withNoArguments().thenReturn(senrol);
			PowerMockito.whenNew(StudentCourseDataDelete.class).withNoArguments().thenReturn(sdel);
			PowerMockito.whenNew(Home.class).withNoArguments().thenReturn(home);
			PowerMockito.when(home.getDays()).thenReturn(21);


		}
		
		

		
		@Test
		public void testStudentSelect1() {
			System.out.println("Testing StudentCourseEnrol selection \n");
			boolean noErrors = true;
			try {
				PowerMockito.when(reader.readLine()).thenReturn("1");
				noErrors = obj.StudentChoices(0, writer, reader);
			} catch (IOException e) {
				noErrors = false;
			} catch (SQLException e) {
				noErrors = false;
			}
			assertTrue(noErrors);
		}
		
		@Test
		public void testStudentSelect2() {
			System.out.println("Testing StudentCourseDataDelete selection \n");
			boolean noErrors = true;
			try {
				PowerMockito.when(reader.readLine()).thenReturn("2");
				noErrors = obj.StudentChoices(0, writer, reader);
			} catch (IOException e) {
				noErrors = false;
			} catch (SQLException e) {
				noErrors = false;
			}
			assertTrue(noErrors);
		}

		@Test
		public void testStudentSelect3() {
			System.out.println("Testing viewCourses selection \n");
			boolean noErrors = true;
			try {
				PowerMockito.doNothing().when(db).viewCourses(0,writer, reader);
				PowerMockito.when(reader.readLine()).thenReturn("3","5","1");
				noErrors = obj.StudentChoices(0, writer, reader);
			} catch (IOException e) {
				noErrors = false;
			} catch (SQLException e) {
				noErrors = false;
			}
			assertTrue(noErrors);
		}
		
		@Test
		public void testStudentSelect4() {
			System.out.println("Testing home.getData selection \n");
			boolean noErrors = true;
			try {
				PowerMockito.doNothing().when(home).getData(writer, reader);
				PowerMockito.when(reader.readLine()).thenReturn("4");
				noErrors = obj.StudentChoices(0, writer, reader);
			} catch (IOException e) {
				noErrors = false;
			} catch (SQLException e) {
				noErrors = false;
			}
			assertTrue(noErrors);
		}
}
