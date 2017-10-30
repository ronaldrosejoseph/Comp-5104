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
import database.StudentDataAdd;
import home.ClerkAction;

		@RunWith(PowerMockRunner.class)
		@PrepareForTest({BufferedReader.class, BufferedWriter.class, StudentDataAdd.class})
		public class StudentDataAddTest {
			
			@Mock
			BufferedReader reader;
			
			@Mock
			BufferedWriter writer;

			@Spy
			StudentDataAdd obj = new StudentDataAdd();
			
			@Mock
			DbConnect dbobj;
			
			@Mock
			ClerkAction clerkact; 
			
			@Before
			public void setUp() throws Exception {
				PowerMockito.doNothing().when(obj).write(Mockito.anyString(), (BufferedWriter)Mockito.anyObject());
				PowerMockito.whenNew(ClerkAction.class).withNoArguments().thenReturn(clerkact);
				PowerMockito.when(clerkact.clientChoices((BufferedWriter) Mockito.any(), (BufferedReader) Mockito.any())).thenReturn(true);
				PowerMockito.whenNew(StudentDataAdd.class).withNoArguments().thenReturn(obj);
				PowerMockito.whenNew(DbConnect.class).withNoArguments().thenReturn(dbobj);
				PowerMockito.doNothing().when(dbobj).courseDataInsert(Mockito.anyInt(),Mockito.anyString(),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyInt());
				PowerMockito.when(dbobj.DisplayCourses(writer,reader)).thenReturn(new int[] {5,6});
				PowerMockito.when(dbobj.isAlpha(Mockito.anyString())).thenReturn(true);
				PowerMockito.when(dbobj.checkCourseAlias(Mockito.anyInt())).thenReturn(0);
			}
					
					
					@Test
					public void testAddCourse() {
						System.out.println("Testing clerk adds a course \n");
						boolean noErrors = true;
						try {
							PowerMockito.when(reader.readLine()).thenReturn("667911","Test", "name","4","\n");
							 obj.addStudent(writer, reader);
						} catch (IOException e) {
							noErrors = false;
						} catch (SQLException e) {
							noErrors = false;
						}
						assertTrue(noErrors);
					}
					



		}
