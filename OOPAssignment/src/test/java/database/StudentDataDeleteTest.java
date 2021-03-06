package database;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

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

import home.ClerkAction;


@RunWith(PowerMockRunner.class)
@PrepareForTest({BufferedReader.class, BufferedWriter.class, StudentDataDelete.class,DbConnect.class})
public class StudentDataDeleteTest {
		@Mock
		BufferedReader reader;
		
		@Mock
		BufferedWriter writer;

		@Spy
		StudentDataDelete obj = new StudentDataDelete();
		
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
			PowerMockito.when(dbobj.DisplayCourses((BufferedWriter) Mockito.any(), (BufferedReader) Mockito.any())).thenReturn(abc);
			PowerMockito.when(dbobj.isPresent(Mockito.anyInt() ,any(int[].class))).thenReturn(true);
			PowerMockito.doNothing().when(dbobj).deleteStudentData(Mockito.anyInt());
			PowerMockito.when(clerkact.clientChoices((BufferedWriter) Mockito.any(), (BufferedReader) Mockito.any())).thenReturn(true);
		}
				
				
				@Test
				public void testDeleteStudent() {
					System.out.println("Testing inputs for clerk delete a course \n");
					boolean noErrors = true;
					try {
						PowerMockito.when(reader.readLine()).thenReturn("111","\n");
						dbobj.deleteStudentData(111);
					} catch (IOException e) {
						noErrors = false;
					} catch (SQLException e) {
						noErrors = false;
					}
					assertTrue(noErrors);
				}

	}
