package database;

import static org.junit.Assert.*;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;

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
	@PrepareForTest({BufferedReader.class, BufferedWriter.class, CourseDataDelete.class,DbConnect.class})
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
		
		int[] abc = new int[50];

		
				
		@Before
		public void setUp() throws Exception {
			PowerMockito.doNothing().when(obj).write(Mockito.anyString(), (BufferedWriter)Mockito.anyObject());
			PowerMockito.whenNew(ClerkAction.class).withNoArguments().thenReturn(clerkact);
			PowerMockito.whenNew(DbConnect.class).withNoArguments().thenReturn(dbobj);
			PowerMockito.when(dbobj.DisplayStudents((BufferedWriter) Mockito.any(), (BufferedReader) Mockito.any())).thenReturn(abc);
			PowerMockito.when(dbobj.isPresent(Mockito.anyInt() ,any(int[].class))).thenReturn(true);
			PowerMockito.doNothing().when(dbobj).deleteCourse(Mockito.anyInt());
			PowerMockito.when(clerkact.clientChoices((BufferedWriter) Mockito.any(), (BufferedReader) Mockito.any())).thenReturn(true);
		}
				
				
				@Test
				public void testdeleteCourse() {
					System.out.println("Testing inputs for course data delete\n");
					boolean noErrors = true;
					try {
						PowerMockito.when(reader.readLine()).thenReturn("111","\n");
						noErrors = obj.deleteCourse(writer, reader);
					} catch (IOException e) {
						noErrors = false;
					} catch (SQLException e) {
						noErrors = false;
					}
					assertTrue(noErrors);
				}
				



	}
