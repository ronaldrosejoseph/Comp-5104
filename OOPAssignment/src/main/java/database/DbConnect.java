package database;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.*;

import org.apache.log4j.Logger;

public class DbConnect {
	private static final Logger log = Logger.getLogger(DbConnect.class);
	static Connection con = null;
	
	 public static Connection DbConnection()
	    {
	        if (con != null) return con;
	       
	        return getdbConnection();
	    }

	    private static Connection getdbConnection()
	    {
	        try
	        {
	        	con= DriverManager.getConnection("jdbc:mysql://localhost:3306/universitydetails","root","Ron@1993");
	        	log.info("Db Connected\n---------------------------");
	        }
	        catch(Exception e)
	        {
	            e.printStackTrace();
	            log.info("Connection failed due to "+ e);
	        }

	        return con;        
	    }
	    

   
   
    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z\\s]*");
    }  		
    
    public boolean isPresent(int dno, int [] sno) 
    {
    	try {
    	for(int j = 0;j<sno.length;j++) {
    		if(dno == sno[j]) {
    			return true;
    		}
    	}
    	}catch (NullPointerException e){ 
		return false;
		}
		return false;
    }
    
    public int[] DisplayStudents(BufferedWriter bufferedWriter, BufferedReader bufferedReader) throws SQLException, IOException 
    {
    	Connection con = DbConnection();
    	Statement stmt=con.createStatement();
    	ResultSet rs=stmt.executeQuery("select StudentNumber,FirstName from Student");
    	int sno[] = new int[50];
    	int i = 0;
    	if (!rs.next()) {
    		write("\nNo Students found\n", bufferedWriter);
    		sno = null;
    	}
    	else {
    	rs.previous();
        while (rs.next()) 
        {
        	 write("Student Number: "+rs.getInt("StudentNumber") + "\tFirstName: " + rs.getString("FirstName"), bufferedWriter);
        	 sno[i] = rs.getInt("StudentNumber");
        	 i++;
        }
    	}
        return sno;
    }
    
    public int[] GetStudents() throws SQLException 
    {
    	Connection con = DbConnection();
    	Statement stmt=con.createStatement();
    	ResultSet rs=stmt.executeQuery("select StudentNumber from Student");
    	int sno[] = new int[50];
    	int i = 0;
        while (rs.next()) 
        {
        	 sno[i] = rs.getInt("StudentNumber");
        	 i++;
        }
        return sno;
    }

    public int[] DisplayCourses(BufferedWriter bufferedWriter, BufferedReader bufferedReader) throws SQLException, IOException 
    {
    	Connection con = DbConnection();
    	Statement stmt=con.createStatement();
    	ResultSet rs=stmt.executeQuery("select Courseid,CourseName,SeatsLeft from course");
    	int cno[] = new int[50];
    	int i = 0;
    	if (!rs.next()) {
    		write("No courses added yet...", bufferedWriter);
    		cno = null;
    		}
    	else {
    	rs.previous();
        while (rs.next()) 
        {
        	 write("Course ID: "+rs.getInt("Courseid") + "\tCourse Name: " + rs.getString("CourseName")+ "\t\t\tSeats left: "+rs.getInt("SeatsLeft"), bufferedWriter);
        	 cno[i] = rs.getInt("Courseid");
        	 i++;
        }
    	}
        return cno;
    }
    
    public int[] DisplayStudentCourses(int sno,BufferedWriter bufferedWriter, BufferedReader bufferedReader) throws SQLException, IOException 
    {
    	Connection con = DbConnection();
    	Statement stmt=con.createStatement();
    	ResultSet rs=stmt.executeQuery("select Courseid,idmark,StudentNumber from mark where StudentNumber = '"+sno+"'");
    	if (!rs.next())
    		return null;
    	rs.previous();
    	int cno[] = new int[50];
    	int i = 0;
        while (rs.next()) 
        {
        	 write("Course ID:  "+rs.getInt("Courseid"), bufferedWriter );
        	 cno[i] = rs.getInt("Courseid");
        	 i++;
        }
        return cno;
    }

	public void viewCourses(int sno, BufferedWriter bufferedWriter, BufferedReader bufferedReader) throws SQLException, IOException {
		Connection con = DbConnection();
    	Statement stmt=con.createStatement();
    	ResultSet rs=stmt.executeQuery("select mark.CourseId,course.CourseName from mark inner join course on mark.CourseId = course.Courseid and mark.StudentNumber = '"+sno+"'");
    	if (!rs.next())
    		write("Nothing to show here", bufferedWriter);
    	else
    		write("\nThese are your courses", bufferedWriter);
    	rs.previous();
    	
        while (rs.next()) 
        {
        	 write("Course ID:  "+rs.getInt("Courseid") +"\t"+"Course Name: "+rs.getString("CourseName"), bufferedWriter);
        }
	}
	
	public synchronized void write(String msg, BufferedWriter bufferedWriter) throws IOException {
		if(null != bufferedWriter) {
			bufferedWriter.write(msg+System.lineSeparator());
			bufferedWriter.flush();
		}
	}

	public void viewGrade(int stno, BufferedWriter bufferedWriter, BufferedReader bufferedReader) throws SQLException, IOException {
		Connection con = DbConnection();
    	Statement stmt=con.createStatement();
    	ResultSet rs=stmt.executeQuery("select course.CourseName, PassFail, TotalM from mark inner join course on  mark.courseid = course.Courseid where StudentNumber = '"+stno+"';");
    	while (rs.next()) 
        {
    		if(rs.getInt("PassFail") == 1) {
    			write("Course Name: "+rs.getString("course.CourseName")+ "\tTotal mark: "+rs.getInt("TotalM")+"\t Pass", bufferedWriter );
    		}
    		else {
            	write("Course Name: "+rs.getString("course.CourseName")+ "\tTotal mark: "+rs.getInt("TotalM")+"\t Fail", bufferedWriter );

    		}
        }   	
    	
    	ResultSet rsnw=stmt.executeQuery("select Grade, Scholarship, MaxNoCourses from student where StudentNumber = '"+stno+"';");
    	float grade = 0;
    	while(rsnw.next()) {
    		grade = rsnw.getFloat("Grade")/10;
    		if(rsnw.getInt("Scholarship") == 1 && rsnw.getInt("MaxNoCourses") == 4) {
        		write("Your total grade is "+grade+" GPA and you are selected for the dean's scholarship",bufferedWriter);
    		}
    		else {
        		write("Your total grade is "+grade+" GPA ",bufferedWriter);
    		}
    	}
	}
	
	public void deansList(BufferedWriter bufferedWriter, BufferedReader bufferedReader) throws SQLException, IOException {
		Connection con = DbConnection();
    	Statement stmt=con.createStatement();
    	ResultSet rs=stmt.executeQuery("select StudentNumber, FirstName, LastName, Scholarship, MaxNoCourses from student;");
    	int flag = 0;
    	while(rs.next()) {
    		if(rs.getInt("Scholarship") == 1 && rs.getInt("MaxNoCourses") == 4) {
    			flag = 1;
        		write("Student Number: "+rs.getInt("StudentNumber")+" \t Name: "+rs.getInt("FirstName")+" "+rs.getInt("LastName")+"",bufferedWriter);
    		}
    	}
    	if(flag == 0) {
    		write("No one selected for dean's list",bufferedWriter);
    		
    	}
	}
	
	
	public ResultSet getCourseStructure() throws SQLException {
		Connection con = DbConnection();
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("select idmark,assignment, midterm , project, FinalExam from course inner join  mark on  mark.courseid = course.Courseid;");
		log.info("Entered getCourseStructure");		
		return rs;
	}

	public void firstMarkWrite(float assigno, int slno) throws SQLException {
		Connection con = DbConnection();
		log.info("Entered firstMarkWrite");		
		Statement nwstmt=con.createStatement();
		nwstmt.executeUpdate("update mark set AssignmentM = '"+assigno+"' where idmark = '"+slno+"';");
		log.info("Updated the row with id: "+slno);		
	}

	public void secondMarkWrite(float mdtno, int slno) throws SQLException {
		Connection con = DbConnection();
		log.info("Entered secondMarkWrite");		
		Statement nwstmt=con.createStatement();
		nwstmt.executeUpdate("update mark set MidtermM = '"+mdtno+"' where idmark = '"+slno+"';");
		log.info("Updated the row with id: "+slno);		
	}

	public void thirdMarkWrite(float pjtno, float feno, int slno) throws SQLException {
		Connection con = DbConnection();
		log.info("Entered thirdMarkWrite");		
		Statement nwstmt=con.createStatement();
		nwstmt.executeUpdate("update mark set ProjectM = '"+pjtno+"' where idmark = '"+slno+"';");
		nwstmt.executeUpdate("update mark set FinalM = '"+feno+"' where idmark = '"+slno+"';");
		log.info("Updated the row with id: "+slno);
	}
	
	public void gradeCalc() throws SQLException {
		Connection con = DbConnection();
		Statement stmt = con.createStatement();
		Statement nwstmt = con.createStatement();
		log.info("Inside grade calculator, gradeCalc");
		ResultSet rs = stmt.executeQuery("select StudentNumber from student;");
		int sno[] = new int[50];
		int i = 0;
		while(rs.next()) {
			sno[i] = rs.getInt("StudentNumber");
			i++;
		}
		float sumMark = 0;
		float count = 0;
		float sumPass = 0;
		float grade;
		for(int j = 0; j<(sno.length); j++)
		{
		ResultSet rsnw = stmt.executeQuery("select sum(TotalM), count(TotalM), sum(PassFail) from mark where StudentNumber = '"+sno[j]+"';");
			while(rsnw.next()) {
				sumMark = rsnw.getInt("sum(TotalM)");
				count = rsnw.getInt("count(TotalM)");
				sumPass = rsnw.getInt("sum(PassFail)");
				grade = sumMark/count;
				if(sumMark != 0) {
					nwstmt.executeUpdate("update student set Grade = '"+grade+"' where StudentNumber = '"+sno[j]+"';");
					log.info("Grade for id "+sno[j]+" is "+grade);
					log.info("sum "+sumMark+" count"+count);
				
				if(sumPass/count == 1) {
					log.info("Student has passed all the courses "+sno[j]);
					nwstmt.executeUpdate("update student set Result = true where StudentNumber = '"+sno[j]+"';");
					if((grade)>=85) 	{
						log.info("Student has scholarship "+sno[j]);
						nwstmt.executeUpdate("update student set Scholarship = true where StudentNumber = '"+sno[j]+"';");
					}
					else {
						log.info("Student has no scholarship "+sno[j]);
						nwstmt.executeUpdate("update student set Scholarship = false where StudentNumber = '"+sno[j]+"';");
					}
				}
				else {
					log.info("Student failed "+sno[j]);
					nwstmt.executeUpdate("update student set Scholarship = false where StudentNumber = '"+sno[j]+"';");
					nwstmt.executeUpdate("update student set Result = false where StudentNumber = '"+sno[j]+"';");
				}
			}
		}
		}
	}

	public boolean hasFinal() throws SQLException {
		Connection con = DbConnection();
		Statement stmt = con.createStatement();
		Statement nwstmt = con.createStatement();
		float totalm = 0;
		int idmark = 0;
		ResultSet rs = stmt.executeQuery("select idmark,AssignmentM,MidTermM,ProjectM,FinalM from mark;");
		log.info("Inside final mark calculator, hasFinal");
		while(rs.next()) {
			idmark = rs.getInt("idmark");
			totalm = rs.getInt("AssignmentM")+rs.getInt("MidTermM")+rs.getInt("ProjectM")+rs.getInt("FinalM");
			log.info("Total mark is "+totalm+" for the course id: "+idmark);
			nwstmt.executeUpdate("update mark set TotalM = '"+totalm+"' where idmark = '"+idmark+"';");
			if(totalm < 70) {
				nwstmt.executeUpdate("update mark set PassFail = false where idmark = '"+idmark+"';");
				log.info("Student failed mark updated on the row id: "+idmark);
			}
			else {
				nwstmt.executeUpdate("update mark set PassFail = true where idmark = '"+idmark+"';");
				log.info("Student passed mark updated on the row id: "+idmark);
			}
		}
		
		return true;
	}

	public void courseDataInsert(int cid, String cna, int ca, int cp, int mt, int fe, int ts, int sl) throws SQLException {
		Connection con = DbConnection();
    	Statement stmt=con.createStatement();
    	stmt.executeUpdate("INSERT INTO universitydetails.course"
    			+ " (`Courseid`,`CourseName`,`Assignment`,`Project`,`MidTerm`,`FinalExam`,`TotalSeats`,`SeatsLeft`)"
    			+  "VALUES ('"+cid+"', '"+cna+"', '"+ca+"', '"+cp+"', '"+mt+"', '"+fe+"','"+ts+"', '"+sl+"')");
    	log.info("Added a course with id: "+cid);
	}

	public ResultSet getCourseId() throws SQLException {
		Connection con = DbConnection();
    	Statement stmt=con.createStatement();
    	ResultSet rs=stmt.executeQuery("select Courseid from course");
    	return rs;
	}

	public void deleteCourse(int dno) throws SQLException {
		Connection con = DbConnection();
    	Statement stmt=con.createStatement();
    	stmt.executeUpdate("delete from course where Courseid in ('"+dno+"')");		
		log.info("Deleted course with id: "+dno);
	 	stmt.executeUpdate("delete from mark where CourseID in ('"+dno+"')");
	 	log.info("Deregister students enrolled for course with id: "+dno);
	}

	public void deleteStudentCourse(int dcno, int stdno) throws SQLException {
		Connection con = DbConnection();
    	Statement stmt=con.createStatement();
    	stmt.executeUpdate("delete from mark where CourseId = '"+dcno+"' and StudentNumber = '"+stdno+"' ;");
	   log.info("Student deregistered from the course: "+dcno);
	   stmt.executeUpdate("update course set SeatsLeft = SeatsLeft + 1 where Courseid = '"+dcno+"'");
	   log.info("Added 1 seat for the course: "+dcno);		
	}
    
	public boolean checkCourse(int stdno, int ecno,BufferedWriter bufferedWriter) throws IOException, SQLException {
		Connection con = DbConnection();
    	Statement stmt=con.createStatement();
    	ResultSet nrs  = stmt.executeQuery("select Courseid from mark where StudentNumber = '"+stdno+"'");
		log.info("Check whether he already selected this course");
	    while (nrs.next()) 
	    {
	    	 if(ecno == nrs.getInt("Courseid")) {
	    		 write("\nYou know what you already have this course!!! ", bufferedWriter);
	    		 log.info("Student already enrolled for the course: " +ecno);		
	    		 return false;
	    	 }
	    }
		return true;
	}

	public boolean seatLeft(int ecno,BufferedWriter bufferedWriter) throws IOException, SQLException {
		Connection con = DbConnection();
    	Statement stmt=con.createStatement();
    	ResultSet stl = stmt.executeQuery("select SeatsLeft from course where Courseid = '"+ecno+"'");
		log.info("Check whether any seats are available for the course: " +ecno);
	    while (stl.next()) 
	    {
	    	 if(stl.getInt("SeatsLeft") < 1) {
	    		 write("\nSeems like the professor gives hot chocolate daily!! No seats available\n Sorry..", bufferedWriter);
	    		 log.info("No seats available for the course: " +ecno);		
	    		 return false;
	    	 }
	    }
		return true;
	}


	public boolean maxCourseCheck(int stdno,BufferedWriter bufferedWriter) throws IOException, SQLException {
		Connection con = DbConnection();
    	Statement stmt=con.createStatement();
    	int count = 0;
		int max = 0;
		ResultSet rs = stmt.executeQuery("select count(Courseid) from mark where StudentNumber = '"+stdno+"'");
	    while (rs.next()) 
	    	 count = rs.getInt("count(Courseid)");
	    log.info("Check the total number of course student enrolled: "+count);
		ResultSet rsnw	= stmt.executeQuery("select MaxNoCourses from student where StudentNumber = '"+stdno+"'");
		while (rsnw.next())
			max = rsnw.getInt("MaxNoCourses");
	   	log.info("Check the total number of course allowed for the student: "+max);
	   	if(max > count)
	   		return true;
	   	else
	   	{
	      	log.info("Student can't enroll because he is not allowed to take courses beyond: " +max);		
	     	write("\nHey Buddy\nI don't think you can handle courses beyond " +max, bufferedWriter);
	     	return false;
	   	}
	}

	public void studentCourseEnrol(int stdno, int ecno) throws SQLException {
		Connection con = DbConnection();
    	Statement stmt=con.createStatement();
    	stmt.executeUpdate("INSERT INTO `universitydetails`.`mark`"
 			   +"(`StudentNumber`,`CourseId`)"
 			   +"VALUES ('"+stdno+"','"+ecno+"')");
 	log.info("Student enrolled for the course: " +ecno);			
 	stmt.executeUpdate("update course set SeatsLeft = SeatsLeft - 1 where Courseid = '"+ecno+"'");
 	log.info("Decresed the number of seats available by 1 for the course: "+ecno);			
	}

	public void studentDataInsert(int sno, String sfna, String slna, int mxcno) throws SQLException {
		Connection con = DbConnection();
    	Statement stmt=con.createStatement();
    	stmt.executeUpdate("INSERT INTO universitydetails.student"
    			+ " (`StudentNumber`,`FirstName`,`LastName`,`MaxNoCourses`,`Grade`,`Result`,`Scholarship`)"
    			+  "VALUES ('"+sno+"','"+sfna+"','"+slna+"' ,'"+mxcno+"',null,null,null)");
      	log.info("Added a student with number: "+sno);		
	}

	public int[] getStudentNumber() throws SQLException {
		Connection con = DbConnection();
    	Statement stmt=con.createStatement();
		log.info("Inside getStudentNumber");		
    	ResultSet rs=stmt.executeQuery("select StudentNumber from student");
    	if (!rs.next())
    		return null;
    	rs.previous();
    	int sno[] = new int[50];
    	int i = 0;
        while (rs.next()) 
        {
        	 sno[i] = rs.getInt("StudentNumber");
        	 i++;
        }
        return sno;
	}

	public void deleteStudentData(int dno) throws SQLException {
		Connection con = DbConnection();
    	Statement stmt=con.createStatement();
    	ResultSet rs=stmt.executeQuery("select Courseid from mark where StudentNumber = '"+dno+"'");
    	int[] course = new int[4];
    	int i = 0;
        while (rs.next()) 
        {
        	course[i] = rs.getInt("Courseid");
        	i++;
        }
        for(int j = 0; j < course.length - 1; j++) {
        	stmt.executeUpdate("update course set SeatsLeft = SeatsLeft + 1 where Courseid = '"+course[j]+"'");
    	log.info("Student deregistered from the course hence seat availabilty increases by 1 for course: " +course[j]);
    }
		stmt.executeUpdate("delete from student where StudentNumber in ('"+dno+"')");
		log.info("Student deleted, student number "+dno);
		stmt.executeUpdate("delete from mark where StudentNumber in ('"+dno+"')");
		log.info("Student course details are deleted");		
	}

	public int checkCourseAlias(int cid) throws SQLException {
		ResultSet rs = getCourseId();
    	int flagnw = 0;
        while (rs.next()) 
        {
        	 if(cid == rs.getInt("Courseid")) {
        		 flagnw = 1;
        		 break;
        	 }
        }
		return flagnw;
		
	}




}















