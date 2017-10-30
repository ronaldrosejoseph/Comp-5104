package home;

import database.CourseDataAdd;
import database.CourseDataDelete;
import database.DbConnect;
import database.StudentDataAdd;
import database.StudentDataDelete;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class ClerkAction {
	private static final Logger log = Logger.getLogger(ClerkAction.class);
	Home home = null;
	public boolean clientChoices(BufferedWriter bufferedWriter, BufferedReader bufferedReader) throws SQLException, IOException {
		home = new Home();
		Home home = new Home();
		if(home.getDays() <= 20) {
			write("1: Add Course \n2: Delete Course \n3: Add Student \n4: Delete Student \n5:  Home", bufferedWriter);
			int numofcases = 5;
			int choice = getChoice(bufferedWriter, bufferedReader, numofcases);
			log.info("Entered clerk action class and got this input: " +choice +" and the number of days is: " +home.getDays());
			try {
				switch(choice) 
					{
					case  1:
						CourseDataAdd cdaobj = new CourseDataAdd();
						cdaobj.addCourse(bufferedWriter,bufferedReader);
						break;
					case 2:
						CourseDataDelete cddobj = new CourseDataDelete();
						cddobj.deleteCourse(bufferedWriter,bufferedReader);
						break;
					case  3:
						StudentDataAdd sdaobj = new StudentDataAdd();
						sdaobj.addStudent(bufferedWriter,bufferedReader);
						break;
					case 4:
						StudentDataDelete sddobj = new StudentDataDelete();
						sddobj.deleteStudent(bufferedWriter,bufferedReader);
						break;
					case 5:
						Home obj = new Home();
						obj.getData(bufferedWriter, bufferedReader);
						break;
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		else if(home.getDays() > 20 && home.getDays() <= 84)
		{
			write("1: Cancel Course \n2: Delete Student \n3:  Home", bufferedWriter);
			int caseno = 3;
			int nwchoice = getChoice(bufferedWriter, bufferedReader, caseno);
			log.info("Entered clerk action class 2nd switch and got this input: " +nwchoice +" and the number of days is: " +home.getDays());			
			switch(nwchoice) 
				{
				case  1:
					CourseDataDelete cddobj = new CourseDataDelete();
					cddobj.deleteCourse(bufferedWriter, bufferedReader);
					break;
				case  2:
					StudentDataDelete sddobj = new StudentDataDelete();
					sddobj.deleteStudent(bufferedWriter, bufferedReader);
					break;
				case 3:
					Home obj = new Home();
					obj.getData(bufferedWriter, bufferedReader);
					break;
				}
			return true;
		}
		else {
			write("Go home and enjoy. Merry Christmas!!!", bufferedWriter);
			write("\n1: Do you wanna see Dean's list \n2: Home", bufferedWriter);
			int nwchoices = getChoice(bufferedWriter, bufferedReader, 2);
			log.info("Entered clerk action class 3rdnd and got this input: " +nwchoices +" and the number of days is: " +home.getDays());
			switch(nwchoices) 
				{
				case  1:
					DbConnect dbobj = new DbConnect();
					dbobj.deansList(bufferedWriter, bufferedReader);
					break;
				case  2:
					home.getData(bufferedWriter, bufferedReader);
					break;
				}
			return true;
		}
	}
	
	private int getChoice(BufferedWriter bufferedWriter, BufferedReader bufferedReader, int count) throws IOException {
		String msg = "";
		int ret = 0;
		int i = 0;
		do {
			try {
				msg = bufferedReader.readLine();
				ret = Integer.valueOf(msg);
				if (ret > count || ret < 1) {
					write("Don't be a stupid...\nCome again!!!\n ", bufferedWriter);
					i = 0;
				}
				else
					i = 1;
			}catch (NumberFormatException e) {
				write("Don't be a stupid...\nCome again!!!\n ", bufferedWriter);
			}
		}while(i == 0);
		return ret;
	}
	
	
	public synchronized void write(String msg, BufferedWriter bufferedWriter) throws IOException {
		if(null != bufferedWriter) {
			bufferedWriter.write(msg+System.lineSeparator());
			bufferedWriter.flush();
		}
	}
	
}
