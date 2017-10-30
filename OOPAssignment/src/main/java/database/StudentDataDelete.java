package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class StudentDataDelete {
	  home.ClerkAction caobj;
	  DbConnect dbobj; 
	private static final Logger log = Logger.getLogger(StudentDataDelete.class);
	  public void deleteStudent(BufferedWriter bufferedWriter, BufferedReader bufferedReader) throws SQLException, IOException 
	    {
		  log.info("Inside the class to delete students");
		  caobj = new home.ClerkAction();
		  dbobj = new DbConnect(); 	
			int sno[] = new int[50];
			sno = dbobj.DisplayStudents(bufferedWriter, bufferedReader);
			write("Enter the student number to delete: ", bufferedWriter);
			int dno = getChoice(bufferedWriter, bufferedReader);
			if(!dbobj.isPresent(dno,sno)) {
				write("No students found. Try again \n", bufferedWriter);
				deleteStudent(bufferedWriter, bufferedReader);
				}
			else 
			{
				dbobj.deleteStudentData(dno);
				write("Deleted one row.\nDisplaying Remaining students", bufferedWriter);
				dbobj.DisplayStudents(bufferedWriter, bufferedReader);
				write("\n\nPress any key to continue: ", bufferedWriter);
		    	bufferedReader.readLine();
		    	caobj.clientChoices(bufferedWriter, bufferedReader);
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
