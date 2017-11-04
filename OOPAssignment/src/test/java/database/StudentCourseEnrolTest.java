//package database;
//
//import static org.junit.Assert.*;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.experimental.theories.DataPoints;
//import org.junit.runner.RunWith;
//import org.mockito.AdditionalMatchers;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.Spy;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//import home.ClerkAction;
//import home.StudentAction;
//
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({BufferedReader.class, BufferedWriter.class, StudentCourseEnrol.class,DbConnect.class})
//public class StudentCourseEnrolTest {
//	
//	
//	@Mock
//	BufferedReader reader;
//	
//	@Mock
//	BufferedWriter writer;
//
//	@Spy
//	StudentCourseEnrol obj = new StudentCourseEnrol();
//	
//	@Spy
//	DbConnect dbobj = new DbConnect();
//	
//	
//	@Mock
//	StudentAction stdact; 
//	
//	
//	
//	
//
//	    
//	    @Before
//	public void setUp() throws Exception {
//	    	int[] abc = new int[5];
//abc[0] = 1;
//int bc =1;
//		PowerMockito.doNothing().when(obj).write(Mockito.anyString(), (BufferedWriter)Mockito.anyObject());
//		PowerMockito.whenNew(StudentAction.class).withNoArguments().thenReturn(stdact);
//		PowerMockito.whenNew(DbConnect.class).withNoArguments().thenReturn(dbobj);
//		PowerMockito.when(dbobj.DisplayCourses((BufferedWriter) Mockito.any(), (BufferedReader) Mockito.any())).thenReturn(new int[] {5,6});
//		PowerMockito.when(dbobj.isPresent(bc ,abc)).thenReturn(true);
//		PowerMockito.when(dbobj.checkCourse(Mockito.anyInt() ,Mockito.anyInt(),writer)).thenReturn(true);
//		PowerMockito.when(dbobj.seatLeft(Mockito.anyInt() , writer)).thenReturn(true);
//		PowerMockito.when(dbobj.maxCourseCheck(Mockito.anyInt() , writer)).thenReturn(true);
//		PowerMockito.doNothing().when(dbobj).viewCourses(Mockito.anyInt(),writer, reader);
//		PowerMockito.doNothing().when(dbobj).studentCourseEnrol(Mockito.anyInt(),Mockito.anyInt());
//	}
//	
//	@Test
//	public void testEnrolCourse() {
//		System.out.println("Testing Enrol a course \n");
//		boolean noErrors = true;
//		try {
//			PowerMockito.when(reader.readLine()).thenReturn("667911","\n");
//			noErrors = obj.enrolCourse(123,writer, reader);
//		} catch (IOException e) {
//			noErrors = false;
//		} catch (SQLException e) {
//			noErrors = false;
//		}
//		assertTrue(noErrors);
//	}
//	
//
//}

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
import home.StudentAction;


@RunWith(PowerMockRunner.class)
@PrepareForTest({BufferedReader.class, BufferedWriter.class, StudentCourseEnrol.class, DbConnect.class})
public class StudentCourseEnrolTest {
	
	
	@Mock
	BufferedReader reader;
	
	@Mock
	BufferedWriter writer;

	@Spy
	StudentCourseEnrol obj = new StudentCourseEnrol();
	
	@Mock
	DbConnect dbobj;
	
	
	@Mock
	StudentAction stdact; 
	
	int[] abc = new int[5];
	
	

	    
	    @Before
	public void setUp() throws Exception {
		PowerMockito.doNothing().when(obj).write(Mockito.anyString(), (BufferedWriter)Mockito.anyObject());
		PowerMockito.whenNew(StudentAction.class).withNoArguments().thenReturn(stdact);
		PowerMockito.whenNew(DbConnect.class).withNoArguments().thenReturn(dbobj);
		PowerMockito.when(dbobj.DisplayCourses((BufferedWriter) Mockito.any(), (BufferedReader) Mockito.any())).thenReturn(abc);
		PowerMockito.when(dbobj.isPresent(Mockito.anyInt() ,any(int[].class))).thenReturn(true);
		PowerMockito.when(dbobj.checkCourse(Mockito.anyInt() ,Mockito.anyInt(),(BufferedWriter)Mockito.anyObject())).thenReturn(true);
		PowerMockito.when(dbobj.seatLeft(Mockito.anyInt() , (BufferedWriter)Mockito.anyObject())).thenReturn(true);
		PowerMockito.when(dbobj.maxCourseCheck(Mockito.anyInt() , (BufferedWriter)Mockito.anyObject())).thenReturn(true);
		PowerMockito.doNothing().when(dbobj).viewCourses(Mockito.anyInt(),(BufferedWriter)Mockito.anyObject(), (BufferedReader) Mockito.any());
		PowerMockito.doNothing().when(dbobj).studentCourseEnrol(Mockito.anyInt(),Mockito.anyInt());
	}
	
	@Test
	public void testEnrolCourse() {
		System.out.println("Testing Enrol a course \n");
		boolean noErrors = true;
		try {
			PowerMockito.when(reader.readLine()).thenReturn("667911","\n");
			noErrors = obj.enrolCourse(1,writer, reader);
		} catch (IOException e) {
			noErrors = false;
		} catch (SQLException e) {
			noErrors = false;
		}
		assertTrue(noErrors);
	}
	

}
