package home;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class Student {
	private static final Logger log = Logger.getLogger(Student.class);
	Student student = null;
	database.DbConnect dbobj = null;
	Home home = null;
	public boolean StudentLogin(BufferedWriter bufferedWriter, BufferedReader bufferedReader) throws SQLException, IOException
	{
		student = new Student();
		dbobj = new database.DbConnect(); 
		home = new Home();
		log.info("Inside Student class");
		int [] scnol = dbobj.GetStudents();
		write("Please enter your student number: ", bufferedWriter);
		String msg = "";
		int sno = 0;
		int i = 0;
		do {
			try {
				msg = bufferedReader.readLine();
				sno = Integer.valueOf(msg);
				if(!dbobj.isPresent(sno,scnol)) 
			      {
			    		write("LoL so funnny!!\nEnter the correct number: ", bufferedWriter);
			    		i = 0;
			     	}
				else
					i = 1;
			}catch (NumberFormatException e) {
				write("LoL so funnny!!\\nEnter the correct number: ", bufferedWriter);
			}
		}while(i == 0);
		write("Please enter your password: ", bufferedWriter);
		int pass = 0;
		int inc = 0;
		i = 0;
		msg = "";
		do {
			try {
				msg = bufferedReader.readLine();
				pass = Integer.valueOf(msg);
				if(pass == 1234)
					i = 0;
				else {
					inc++;
					if(inc == 3) {
						Clerk.write("\n--------------\nWrong password entered thrice. Redirecting..\n", bufferedWriter);
						home.getData(bufferedWriter, bufferedReader);
						return false;
					}
					else {
					student.write("Wrong Password...\nCome again!!!\n ", bufferedWriter);
					i = 1;
					}
					}
			}catch (NumberFormatException e) {
				inc++;
				if(inc == 3) {
					student.write("\n-------------------\nWrong password entered thrice. Redirecting..\\n\"", bufferedWriter);
					home.getData(bufferedWriter, bufferedReader);
					return false;
				}
				else	{
				student.write("Wrong Password...\nCome again!!!\n ", bufferedWriter);
				i = 1;
				}
				}
							
		}while(i == 1);
	
		if (pass == 1234) {
			log.info("Student is authenticated");
			StudentAction studentact = new StudentAction();
			studentact.StudentChoices(sno,bufferedWriter,bufferedReader);
			return true;
		}
		return false;
	
	}
	

	public synchronized void write(String msg, BufferedWriter bufferedWriter) throws IOException {
		if(null != bufferedWriter) {
			bufferedWriter.write(msg+System.lineSeparator());
			bufferedWriter.flush();
		}
	}
}
