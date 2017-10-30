package database;

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
import home.ClerkAction;

	@RunWith(PowerMockRunner.class)
	@PrepareForTest({BufferedReader.class, BufferedWriter.class, CourseDataDelete.class})
	public class CourseDataDeleteTest {
		
		@Mock
		BufferedReader reader;
		
		@Mock
		BufferedWriter writer;

		@Spy
		CourseDataDelete obj = new CourseDataDelete();
		
		@Mock
		DbConnect dbobj;
		
		@Mock
		ClerkAction clerkact; 
		
		
				
		@Before
		public void setUp() throws Exception {
			int[] abc = new int[50];
			PowerMockito.doNothing().when(obj).write(Mockito.anyString(), (BufferedWriter)Mockito.anyObject());
			PowerMockito.whenNew(ClerkAction.class).withNoArguments().thenReturn(clerkact);
			PowerMockito.when(clerkact.clientChoices((BufferedWriter) Mockito.any(), (BufferedReader) Mockito.any())).thenReturn(true);
			PowerMockito.whenNew(CourseDataDelete.class).withNoArguments().thenReturn(obj);
			PowerMockito.whenNew(DbConnect.class).withNoArguments().thenReturn(dbobj);
			PowerMockito.when(dbobj.DisplayCourses(writer,reader)).thenReturn(new int[] {5,6});
			PowerMockito.when(dbobj.isPresent(123,abc)).thenReturn(true);
			PowerMockito.doNothing().when(dbobj).deleteCourse(Mockito.anyInt());
		}
				
				
				@Test
				public void testAddCourse() {
					System.out.println("Testing clerk delete a course \n");
					boolean noErrors = true;
					int[] abc = new int[50];
					try {
						PowerMockito.when(dbobj.isPresent(123,abc)).thenReturn(true);
						PowerMockito.when(reader.readLine()).thenReturn("667911","\n");
						noErrors = obj.deleteCourse(writer, reader);
					} catch (IOException e) {
						noErrors = false;
					} catch (SQLException e) {
						noErrors = false;
					}
					assertTrue(noErrors);
				}
				



	}
