package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.SQLException;
import org.apache.log4j.Logger;


public class CourseDataAdd {
	private static final Logger log = Logger.getLogger(CourseDataAdd.class);
	home.ClerkAction caobj = null;
	DbConnect dbobj = null;
    public boolean addCourse(BufferedWriter bufferedWriter, BufferedReader bufferedReader) throws SQLException, IOException
    {
    	log.info("Inside class to add new courses to db");
    	caobj = new home.ClerkAction();
    	dbobj = new DbConnect();
    	write("CourseID has 2 digit depatment code and 4 digit course code\nDepartment Code CS 11, EE 22, ME 33, CV 44, EM 55, AME 66, AE 77, ME 88 and CHE 99\nEnter Course Number: ", bufferedWriter);
    	int cid = 0;
    	int flag = 0;
    	do{
    			cid = getChoice(bufferedWriter, bufferedReader);
    			while(cid > 999999 || cid < 99999) {
    	    		write("Accepts only 6 digits...\nCome again!!! ", bufferedWriter);
    	    		cid = getChoice(bufferedWriter, bufferedReader);
    	    	}
    		    			
    			if (cid/100000 == ((cid/10000)%10)) {
    				flag = 0;
    			}
    			else {
    				write("We don't have that department. Try again with proper code...", bufferedWriter);
    				flag = 1;
    			}
    				    			
    	}while(flag == 1);
    	
    	
    	log.info("Entered Course id: "+cid);

    	write("Enter Course Name: ", bufferedWriter);
    	String cna = getChoiceS(bufferedWriter, bufferedReader);
    	log.info("Entered Course Name: "+cna);

       	
    	write("Enter the number of Assignments: ", bufferedWriter);
       	int ca = getChoice(bufferedWriter, bufferedReader);
    	log.info("Entered number of Assignments: "+ca);

    	
    	write("Enter the number of Projects: ", bufferedWriter);
    	int cp = getChoice(bufferedWriter, bufferedReader);
    	log.info("Entered number of Projects: "+cp);

    	
    	write("Enter the Number of Mid Term: ", bufferedWriter);
    	int mt = getChoice(bufferedWriter, bufferedReader);
    	log.info("Entered number of  Mid Term: "+mt);

    	
    	write("Enter the Number of Final Exam: ", bufferedWriter);
    	int fe = getChoice(bufferedWriter, bufferedReader);
    	log.info("Entered number of Final Exam: "+fe);

    	
    	write("Enter the Number of Total Seat: ", bufferedWriter);
    	int ts = getChoice(bufferedWriter, bufferedReader);
    	int sl = ts;
    	log.info("Entered number of Total Seat: "+ts);
    	
    	int flagnw = dbobj.checkCourseAlias(cid);
        if (flagnw == 0) {
        dbobj.courseDataInsert(cid,cna,ca,cp,mt,fe,ts,sl);
    	dbobj.DisplayCourses(bufferedWriter, bufferedReader);
    	write("\n\nPress enter to continue: ", bufferedWriter);
    	bufferedReader.readLine();
    	caobj.clientChoices(bufferedWriter, bufferedReader);
        }
        else {
        	log.info("Course already exits, course id: "+cid);
        	write("Course ID already exits! Better be careful next time, reenter all details", bufferedWriter);
        	caobj.clientChoices(bufferedWriter, bufferedReader);
        	return false;
        }
		return true;
    	
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
						write("Don't be a stupid...\nEnter characters only!!!\nTry Again ", bufferedWriter);
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
