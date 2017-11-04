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
import home.StudentAction;

		@RunWith(PowerMockRunner.class)
		@PrepareForTest({BufferedReader.class, BufferedWriter.class, StudentCourseDataDelete.class,  DbConnect.class})
		public class StudentCourseDataDeleteTest {
			
			@Mock
			BufferedReader reader;
			
			@Mock
			BufferedWriter writer;

			@Spy
			StudentCourseDataDelete obj = new StudentCourseDataDelete();
			
			@Mock
			DbConnect dbobj;
			
			@Mock
			StudentAction stdact; 
			
			int[] abc = new int[5];
					
			@Before
			public void setUp() throws Exception {
				int[] abc = new int[50];
				PowerMockito.doNothing().when(obj).write(Mockito.anyString(), (BufferedWriter)Mockito.anyObject());
				PowerMockito.whenNew(StudentAction.class).withNoArguments().thenReturn(stdact);
				PowerMockito.whenNew(DbConnect.class).withNoArguments().thenReturn(dbobj);
				PowerMockito.when(dbobj.DisplayStudentCourses(Mockito.anyInt(),(BufferedWriter) Mockito.any(), (BufferedReader) Mockito.any())).thenReturn(abc);
				PowerMockito.when(dbobj.isPresent(Mockito.anyInt() ,any(int[].class))).thenReturn(true);
				PowerMockito.doNothing().when(dbobj).deleteStudentCourse(Mockito.anyInt(),Mockito.anyInt());
				PowerMockito.doNothing().when(dbobj).viewCourses(Mockito.anyInt(),(BufferedWriter)Mockito.anyObject(), (BufferedReader) Mockito.any());
				PowerMockito.when(stdact.StudentChoices(Mockito.anyInt(),(BufferedWriter) Mockito.any(), (BufferedReader) Mockito.any())).thenReturn(true);
			}
					
					
					@Test
					public void testdropCourse() {
						System.out.println("Testing inputs for student course drop\n");
						boolean noErrors = true;
						try {
							PowerMockito.when(reader.readLine()).thenReturn("667911","\n");
							noErrors = obj.dropCourse(123,writer, reader);
						} catch (IOException e) {
							noErrors = false;
						} catch (SQLException e) {
							noErrors = false;
						}
						assertTrue(noErrors);
					}
					



		}

