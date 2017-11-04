package database;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import home.StudentAction;

public class StudentCourseDataDelete {
	private static final Logger log = Logger.getLogger(StudentCourseDataDelete.class);
	StudentAction stdact = null;
	DbConnect dbobj = null;
	  public boolean dropCourse(int stdno, BufferedWriter bufferedWriter, BufferedReader bufferedReader) throws SQLException, IOException {
		   stdact = new StudentAction();
		   dbobj = new DbConnect();
		   int [] crslt = dbobj.DisplayStudentCourses(stdno, bufferedWriter, bufferedReader);
		   if (crslt != null) 
		   {
			   write("These are your courses\n", bufferedWriter);
			   write("Enter the Course Id of the course to drop: ", bufferedWriter);
			   String msg = "";
				int dcno = 0;
				int i = 0;
				do {
					try {
						msg = bufferedReader.readLine();
						dcno = Integer.valueOf(msg);
						if(!dbobj.isPresent(dcno,crslt)) 
					      {
							    log.info("No courses found: "+dcno);
					    		write("LoL so funnny!!\nEnter the correct number: ", bufferedWriter);
					    		i = 0;
					     	}
						else
							i = 1;
					}catch (NumberFormatException e) {
					    log.info("Invalid input: "+dcno);
						write("LoL so funnny!!\\nEnter the correct number: ", bufferedWriter);
					}
				}while(i == 0);
				dbobj.deleteStudentCourse(dcno,stdno);
			   write("Displaying your course(s)", bufferedWriter);
			   dbobj.viewCourses(stdno, bufferedWriter, bufferedReader);
			   write("\n\nPress enter to continue: ", bufferedWriter);
			   bufferedReader.readLine();
			   stdact.StudentChoices(stdno, bufferedWriter, bufferedReader);
			   return true;
			}
		   else {
			   log.info("Student doesn't have any courses to delete, studentnumber: "+stdno);
			   write("Hey Lazy fellow...\nYou don't have any courses", bufferedWriter);	
			   stdact.StudentChoices(stdno, bufferedWriter, bufferedReader);
			   return false;
		   }
	  }
	  
	  public synchronized void write(String msg, BufferedWriter bufferedWriter) throws IOException {
			if(null != bufferedWriter) {
				bufferedWriter.write(msg+System.lineSeparator());
				bufferedWriter.flush();
			}
		}
	  
	}


