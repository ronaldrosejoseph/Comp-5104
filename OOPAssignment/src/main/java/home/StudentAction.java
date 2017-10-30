package home;

import database.DbConnect;
import database.StudentCourseDataDelete;
import database.StudentCourseEnrol;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;


public class StudentAction {
	private static final Logger log = Logger.getLogger(StudentAction.class);
	Home home = null;
	StudentAction studentact = null;
	public boolean StudentChoices(int stno, BufferedWriter bufferedWriter, BufferedReader bufferedReader) throws SQLException, IOException {
		DbConnect dbobj = new DbConnect();
		home = new Home();
		studentact = new StudentAction(); 
		
		if(home.getDays() > 20 && home.getDays() <= 34) {
			write("1: Enrol Course \n2: Drop Course \n3: My Courses\n4: Home", bufferedWriter);
			int noofcases = 4;
			int choice = getChoice(bufferedWriter,bufferedReader,noofcases);
			log.info("Entered student action class and got this input: " +choice +" and the number of days is: " +home.getDays());
				switch(choice) 
					{
				
					case  1:
						StudentCourseEnrol sceobj = new StudentCourseEnrol();
						sceobj.enrolCourse(stno,bufferedWriter,bufferedReader);
						break;
					case 2:
						StudentCourseDataDelete scddobj = new StudentCourseDataDelete();
						scddobj.dropCourse(stno,bufferedWriter,bufferedReader);
						break;
					case  3:
						dbobj.viewCourses(stno, bufferedWriter, bufferedReader);
						write("\n\nPress enter to continue",bufferedWriter);
						bufferedReader.readLine();
						StudentChoices(stno, bufferedWriter, bufferedReader);
						break;
					case 4:
						home.getData(bufferedWriter, bufferedReader);
						break;
					}
			return true;
		}
		else if(home.getDays() > 34 && home.getDays() <= 48)
		{
			write("1: Drop Course\n2: My Courses\n3: Home ", bufferedWriter);
			int nwchoice = getChoice(bufferedWriter, bufferedReader, 3);
			log.info("Entered student action class 2nd and got this input: " +nwchoice +" and the number of days is: " +home.getDays());
			switch(nwchoice) 
				{
				case  1:
					StudentCourseDataDelete scddobj = new StudentCourseDataDelete();
					scddobj.dropCourse(stno, bufferedWriter, bufferedReader);
					break;
				case  2:
					dbobj.viewCourses(stno, bufferedWriter, bufferedReader);
					write("\n\nPress enter to continue",bufferedWriter);
					bufferedReader.readLine();
					StudentChoices(stno, bufferedWriter, bufferedReader);
					break;
				case 3:
					home.getData(bufferedWriter, bufferedReader);
					break;
				}
			return true;
		}
		else if(home.getDays() > 48 && home.getDays() < 84) {
			dbobj.viewCourses(stno, bufferedWriter, bufferedReader);
			write("\n\nPress enter to continue",bufferedWriter);
			bufferedReader.readLine();
			home.getData(bufferedWriter, bufferedReader);
			return true;
		}
		else if(home.getDays() <= 20) {
			write("You can't enrol for courses now.\nCome after "+(21-home.getDays())+" days", bufferedWriter);
			home.getData(bufferedWriter, bufferedReader);
			return false;
		}
		else if(home.getDays() >= 84) {
			write("Go home and enjoy. Merry Christmas!!!", bufferedWriter);
			write("\n1: Do you wanna see your grades \n2: Home", bufferedWriter);
			int nwchoices = getChoice(bufferedWriter, bufferedReader, 2);
			log.info("Entered student action class 3rdnd and got this input: " +nwchoices +" and the number of days is: " +home.getDays());
			switch(nwchoices) 
				{
				case  1:
					dbobj.viewGrade(stno, bufferedWriter, bufferedReader);
					write("Press enter to continue",bufferedWriter);
					bufferedReader.readLine();
					StudentChoices(stno, bufferedWriter, bufferedReader);
					return true;
				case  2:
					home.getData(bufferedWriter, bufferedReader);
					break;
				}
			return true;
		}
		return true;
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
