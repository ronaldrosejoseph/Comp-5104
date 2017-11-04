package database;

import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
@PrepareForTest({BufferedReader.class, BufferedWriter.class, DbConnect.class})
public class DbConnectTest {
	Connection con = DbConnect.DbConnection();
	
	@Spy
	DbConnect dbobj = new DbConnect();
	
	@Mock
	BufferedReader reader;
	
	@Mock
	BufferedWriter writer;
	
	@Before
	public void setUp() throws Exception {
		PowerMockito.doNothing().when(dbobj).write(Mockito.anyString(), (BufferedWriter)Mockito.anyObject());
	}

	@Test
	public void delCoursetest() throws SQLException {
		Statement stmt=con.createStatement();
    	int dno = 772003;
    	boolean noError = false;
    	boolean delete = false;

    	ResultSet rs=stmt.executeQuery("select Courseid from course where Courseid in ('"+dno+"');");
    	while(rs.next()) {
    		dbobj.deleteCourse(dno);
    		delete = true; 
    	}
    	ResultSet rss=stmt.executeQuery("select Courseid from course where Courseid in ('"+dno+"');");
    	if(rss.next())
    		noError = false;
    	else
    		noError = true;
    	
	assertTrue(noError);
	assertTrue(delete);
	}
	
	
	@Test
	public void dropStudentCoursetest() throws SQLException {
		Statement stmt=con.createStatement();
    	int dcno = 220001;
    	int stdno = 123;
    	boolean noError = false;
    	boolean delete = false;
    	int seatleft = 0;
    	int seatleftAt = 0;
    	ResultSet renw = stmt.executeQuery("select SeatsLeft from course  where Courseid = '"+dcno+"';");
    	while(renw.next())
    		seatleft = renw.getInt(1);
    	
    	System.out.println("Seat left = "+seatleft );
    	ResultSet rs=stmt.executeQuery("select CourseId from mark where CourseId  = '"+dcno+"' and StudentNumber = '"+stdno+"' ;");
    	while(rs.next()) {
    		dbobj.deleteStudentCourse(dcno,stdno);
    		delete = true; 
    	}
    	
    	ResultSet rss=stmt.executeQuery("select CourseId from mark where CourseId = '"+dcno+"' and StudentNumber = '"+stdno+"' ;");
    	if(rss.next())
    		noError = false;
    	else
    		noError = true;
    	
    	ResultSet renwAt = stmt.executeQuery("select SeatsLeft from course  where Courseid = '"+dcno+"';");
    	while(renwAt.next())
    		seatleftAt = renwAt.getInt(1);
    	System.out.println("Seat left after deletion = "+seatleftAt );
    	assertEquals(seatleft + 1,seatleftAt );
	assertTrue(noError);
	assertTrue(delete);

	}
	
	
	@Test
	public void delStudenttest() throws SQLException {
		Statement stmt=con.createStatement();
    	int dno = 111;
    	boolean noError = false;
    	boolean delete = false;
    	ResultSet rs=stmt.executeQuery("select Courseid from mark where StudentNumber = '"+dno+"'");
    	int[] course = new int[4];
    	int i = 0;
        while (rs.next()) 
        {
        	course[i] = rs.getInt("Courseid");
        	i++;
        }
        ResultSet rssl;
        int[] seatleft = new int[4];
        for(int j = 0; j < course.length; j++) {
        	rssl = stmt.executeQuery("select SeatsLeft from course where Courseid = '"+course[j]+"'");
        	while(rssl.next()) 
        		seatleft[j] = rssl.getInt("SeatsLeft");
        	System.out.println("Seats left before deleting student for the course "+course[j]+" is "+seatleft[j]);
    }
    	ResultSet rsnw=stmt.executeQuery("select StudentNumber from student where StudentNumber in ('"+dno+"');");
    	while(rsnw.next()) {
    		dbobj.deleteStudentData(dno);
    		delete = true; 
    	}
    	ResultSet rss=stmt.executeQuery("select StudentNumber from student where StudentNumber in ('"+dno+"');");
    	if(rss.next())
    		noError = false;
    	else
    		noError = true;
    	
    	ResultSet rsssd=stmt.executeQuery("select StudentNumber from mark where StudentNumber in ('"+dno+"');");
    	if(rsssd.next())
    		noError = false;
    	else
    		noError = true;
    	ResultSet rssldl;
          int[] seatleftAt = new int[4];
          for(int j = 0; j < course.length; j++) {
          	rssldl = stmt.executeQuery("select SeatsLeft from course where Courseid = '"+course[j]+"'");
        	while(rssldl.next()) 
        		seatleftAt[j] = rssldl.getInt("SeatsLeft");
          	System.out.println("Seats left after deleting student for the course "+course[j]+" is "+seatleftAt[j]);
        	assertEquals(seatleft[j]+1, seatleftAt[j]);
      }
	assertTrue(noError);
	assertTrue(delete);
	}
	
	
	@Test
	public void enrolStudenttest() throws SQLException, IOException {
		System.out.println("Testing Student Enrol");
		Statement stmt=con.createStatement();
    	int stdno = 201703001;
    	int ecno = 550001;
    	int seatleft = 0;
    	boolean noError = false;
    	boolean add = false;
    	ResultSet rssft=stmt.executeQuery("select StudentNumber from student where StudentNumber in ('"+stdno+"');");
    	if(rssft.next() && dbobj.checkCourse(stdno,ecno, writer) && dbobj.seatLeft(ecno,  writer) && dbobj.maxCourseCheck(stdno,  writer))
    	{
    	ResultSet rs=stmt.executeQuery("select SeatsLeft from course where Courseid = '"+ecno+"';");
        while (rs.next()) 
        {
        	seatleft = rs.getInt("SeatsLeft");
          	System.out.println("Seats left before student enrolled for the course "+ecno+" is "+seatleft);
        }

   		dbobj.studentCourseEnrol(stdno, ecno) ;
   		add = true; 
   		
    	ResultSet rss=stmt.executeQuery("select StudentNumber from mark where StudentNumber in ('"+stdno+"') and CourseId = '"+ecno+"';");
    	if(rss.next())
    		noError = true;
    	else
    		noError = false;
    	
    	  ResultSet rssl;
          int seatleftAt = 0;
          	rssl = stmt.executeQuery("select SeatsLeft from course where Courseid = '"+ecno+"';");
          	while(rssl.next())
          		seatleftAt = rssl.getInt("SeatsLeft");
          	System.out.println("Seats left after student enrolled for the course "+ecno+" is "+seatleftAt);
        	assertEquals(seatleft -1, seatleftAt);

		assertTrue(add);
		assertTrue(noError);

    	}
    	else {
          	System.out.println("Student is not allowed to enrol the course "+ecno+" check logs");
    		add = false;
    		assertTrue(add);
    	}
	}

	@Test
	public void courseAddtest() throws SQLException, IOException {
		System.out.println("Testing course Add");
		Statement stmt=con.createStatement();
    	int cid = 201703001;
    	String cna = "test";
    	int ca = 2;
    	int cp = 2;
    	int mt = 2;
    	int fe = 2;
    	int ts = 10;
    	int sl = ts;
    	boolean add = false;
    	boolean noError = false;
    	if(dbobj.checkCourseAlias(cid) == 0) {
        dbobj.courseDataInsert(cid,cna,ca,cp,mt,fe,ts,sl);
        ResultSet rs=stmt.executeQuery("select * from course where Courseid = '"+cid+"';");
        while (rs.next()) 
        {
        	if(cid == rs.getInt(1) && cna == rs.getString(2) && ca == rs.getInt(3) && cp == rs.getInt(4) && mt == rs.getInt(5) && fe == rs.getInt(6) && ts == rs.getInt(7) && sl == rs.getInt(8)) {
              	System.out.println("Not added properly");
              	add = false;
              	assertTrue(add);
        	}
        	else {
        		add = true;
        		noError = true;
        		assertTrue(add);
        		}
        }
        assertTrue(noError);
        
    }
    	else {
    		add = false;
    		assertTrue(add);
    	}

}
	
	
	@Test
	public void studentAddtest() throws SQLException, IOException {
		System.out.println("Testing Student Add");
		Statement stmt=con.createStatement();
    	int sno = 2;
    	String sfna = "test";
    	String slna = "test";
    	int mxcno = 2;
    	boolean add = false;
    	boolean noError = false;
    	int snoarr[] = new int[50];
    	snoarr= dbobj.getStudentNumber();
        if(!dbobj.isPresent(sno,snoarr)) {
   		dbobj.studentDataInsert(sno,sfna,slna,mxcno);
        ResultSet rss=stmt.executeQuery("select * from student where StudentNumber = '"+sno+"';");
        while (rss.next()) 
        {
        	if(sno == rss.getInt(1) && sfna == rss.getString(2) && slna == rss.getString(3) && mxcno == rss.getInt(4)) {
              	System.out.println("Not added properly");
              	add = false;
              	assertTrue(add);
        	}
        	else {
        		System.out.println("Student added");
        		add = true;
        		noError = true;
        		assertTrue(add);
        		}
        }
        assertTrue(noError);
        
    }
    	else {
    		add = false;
    		assertTrue(add);
    	}

}
	
	
	
}
