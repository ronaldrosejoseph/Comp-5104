package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class CourseDataDelete {
	home.ClerkAction caobj;
	DbConnect dbobj;
	private static final Logger log = Logger.getLogger(CourseDataDelete.class);
	   public boolean deleteCourse(BufferedWriter bufferedWriter, BufferedReader bufferedReader) throws SQLException, IOException {
		   log.info("Inside the class to delete course");
		   caobj = new home.ClerkAction();
		   dbobj = new DbConnect();
	    	int cno[] = new int[50];
	    	cno = dbobj.DisplayCourses(bufferedWriter, bufferedReader);
	    	if(cno != null) {
	    	write("\nEnter the course id to delete: ", bufferedWriter);
	    	int dno = getChoice(bufferedWriter, bufferedReader);
			   log.info("Check the course is present: "+dno);
	    	if(!dbobj.isPresent(dno,cno)) {
     		    log.info("The course is not present: "+dno);
	    		write("\nAre you Drunkun!!! No such course exists... Try again\n", bufferedWriter);
	    		deleteCourse(bufferedWriter, bufferedReader); 
				return false;
	    	}
	    	else {
	    		dbobj.deleteCourse(dno);
		    	write("Deleted one row.\nDisplaying Remaining Courses", bufferedWriter);
		    	dbobj.DisplayCourses(bufferedWriter, bufferedReader);
		    	write("\n\nPress enter to continue: ", bufferedWriter);
		    	bufferedReader.readLine();
		    	caobj.clientChoices(bufferedWriter, bufferedReader);
				return true;
	    	}
	    	}
	    	else
	    	{
	    		caobj.clientChoices(bufferedWriter, bufferedReader);
				return true;
	    	}
	    }
	   
		 private int getChoice(BufferedWriter bufferedWriter, BufferedReader bufferedReader) throws IOException {
				String msg = "";
				int ret = 0;
				int check = 0;
				do {
					try {
						msg = bufferedReader.readLine();
						ret = Integer.valueOf(msg);
						check = 1;
					}catch (NumberFormatException e) {
						write("Don't be a stupid...\nEnter an integer!!!\n ", bufferedWriter);
					}
				}while(check == 0);
				return ret;
			}
		 
	   public synchronized void write(String msg, BufferedWriter bufferedWriter) throws IOException {
			if(null != bufferedWriter) {
				bufferedWriter.write(msg+System.lineSeparator());
				bufferedWriter.flush();
			}
	   }
}
