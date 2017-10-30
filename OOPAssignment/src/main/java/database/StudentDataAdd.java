package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import home.ClerkAction;

public class StudentDataAdd {	
	private static final Logger log = Logger.getLogger(StudentDataAdd.class);
	ClerkAction caobj = null;
	DbConnect dbobj = null;
	 public void addStudent(BufferedWriter bufferedWriter, BufferedReader bufferedReader) throws SQLException, IOException 
	    {
		    log.info("Inside class to add new students to db");
	    	caobj = new ClerkAction();
		 	dbobj = new DbConnect();
	    	
	    	write("Enter Student Number: ", bufferedWriter);
	    	int sno = getChoice(bufferedWriter, bufferedReader);
	    	
	    	write("Enter Student First Name: ", bufferedWriter);
	    	String sfna = getChoiceS(bufferedWriter, bufferedReader);
	    	
	    	write("Enter Student Last Name: ", bufferedWriter);
	    	String slna = getChoiceS(bufferedWriter, bufferedReader);
	    	
	    	write("Enter Max Number of Courses\n4:Full Time or 2:Part Time: ", bufferedWriter);
	    	int mxcno = getChoice(bufferedWriter, bufferedReader);
	    	if (mxcno > 2 && mxcno != 4)
	    	{
	    		write("It won't work here\nAsuming that as 4\nFull time Study, No Party!!! ", bufferedWriter);
	    		mxcno = 4;
	    	}
	    	else if(mxcno != 2 && mxcno < 2)
	    	{
	    		write("Don't be a fool\nAsuming that as 2\nPart time Study, Can Party!!! ", bufferedWriter);
	    		mxcno = 2;
	    	}

	    	ResultSet rs= dbobj.getStudentNumber();
	    	int flag = 0;
	        while (rs.next()) 
	        {
	        	 if(sno == rs.getInt("StudentNumber")) {
	        		 flag = 1;
	        		 break;
	        	 }
	        }
	        
	        if (flag == 0) {
	        	dbobj.studentDataInsert(sno,sfna,slna,mxcno);
		    	write("Student is added", bufferedWriter);
		    	dbobj.DisplayStudents(bufferedWriter, bufferedReader);
		    	write("\n\nPress any key to continue: ", bufferedWriter);
		    	bufferedReader.readLine();
		    	caobj.clientChoices(bufferedWriter, bufferedReader);
	        }
	        else {
	        	log.info("Student already exits, student number: "+sno);
	        	write("Student number already exits! Better be careful next time, reenter all details", bufferedWriter);
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
		
		private String getChoiceS(BufferedWriter bufferedWriter, BufferedReader bufferedReader) throws IOException {
			String msg = "";
			int check = 0;
			do {
				try {
					msg = bufferedReader.readLine();
					while(!dbobj.isAlpha(msg)) {
							write("Don't be a stupid...\nEnter characters only!!!\nTry again: ", bufferedWriter);
							msg = bufferedReader.readLine();
						}
						check = 1;
				}catch (NumberFormatException e) {
					write("Don't be a stupid...\nEnter characters only!!!\n ", bufferedWriter);
				}
			}while(check == 0);
			return msg;
		}
		
		
	    
	    public synchronized void write(String msg, BufferedWriter bufferedWriter) throws IOException {
			if(null != bufferedWriter) {
				bufferedWriter.write(msg+System.lineSeparator());
				bufferedWriter.flush();
			}
		}

	    
	    
}
