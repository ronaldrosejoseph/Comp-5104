package database;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import home.StudentAction;

public class StudentCourseEnrol {
	private static final Logger log = Logger.getLogger(StudentCourseEnrol.class);
	StudentAction stdact = null;
	DbConnect dbobj = null;
	 public void enrolCourse(int stdno, BufferedWriter bufferedWriter, BufferedReader bufferedReader) throws SQLException, IOException {
		 stdact = new StudentAction();
		 dbobj = new DbConnect();
		 log.info("Inside enrollement method");
		   write("These are the courses offered",bufferedWriter);
		   int [] crslt = dbobj.DisplayCourses(bufferedWriter, bufferedReader);
		   write("Enter the Course Id: ", bufferedWriter);
		   		
			String msg = "";
			int ecno = 0;
			int i = 0;
			do {
				try {
					msg = bufferedReader.readLine();
					ecno = Integer.valueOf(msg);
					if(!dbobj.isPresent(ecno,crslt)) 
				      {
				    		write("LoL so funnny!!\nEnter the correct number: ", bufferedWriter);
				    		i = 0;
				     	}
					else
						i = 1;
				}catch (NumberFormatException e) {
					write("LoL so funnny!!\nEnter the correct number: ", bufferedWriter);
				}
			}while(i == 0);
		
		if(dbobj.checkCourse(stdno,ecno, bufferedWriter) && dbobj.seatLeft(ecno,  bufferedWriter) && dbobj.maxCourseCheck(stdno,  bufferedWriter))
		{
		        	dbobj.studentCourseEnrol(stdno,ecno);
		        	write("\nDisplaying your course(s): ", bufferedWriter);
		        	dbobj.viewCourses(stdno, bufferedWriter, bufferedReader);
		        	write("\n\nPress enter to continue: ", bufferedWriter);
		        	bufferedReader.readLine();
		        	stdact.StudentChoices(stdno, bufferedWriter, bufferedReader); 
		}
        else 
         	stdact.StudentChoices(stdno, bufferedWriter, bufferedReader); 
       
}
	 


	 public synchronized void write(String msg, BufferedWriter bufferedWriter) throws IOException {
			if(null != bufferedWriter) {
				bufferedWriter.write(msg+System.lineSeparator());
				bufferedWriter.flush();
			}
		}
}
