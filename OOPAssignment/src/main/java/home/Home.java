package home;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import org.apache.log4j.Logger;

import database.MarkGenerator;

public class Home 
	{
	private static final Logger log = Logger.getLogger(Home.class);
	static Date date = new Date();
	long startTime = date.getTime();
	MarkGenerator mkobj = new MarkGenerator();
	private final ScheduledExecutorService scheduler =
   	     Executors.newScheduledThreadPool(1);
	Home home =  null;
	public void getData(BufferedWriter bufferedWriter, BufferedReader bufferedReader) throws IOException, SQLException 
	{
		home = new Home();
		log.info("\n---------------------------------------\n\nStarting from home\n\n-------------------------------------");
		int choice = 0;
		write("\n1. Clerk \n2. Student \nEnter your choice: ", bufferedWriter);
		
			choice = getChoice(bufferedWriter,bufferedReader);
			log.info("User gives this option: "+choice);
			switch(choice) 
				{
				case  1:
					Clerk obj = new Clerk();
					obj.Validation(bufferedWriter,bufferedReader);
					break;
				case 2:
					Student stobj = new Student();
					stobj.StudentLogin(bufferedWriter,bufferedReader);
					break;
				}
	}


	private int getChoice(BufferedWriter bufferedWriter, BufferedReader bufferedReader) throws IOException {
		String msg = "";
		int ret = 0;
		int i = 0;
		do {
			try {
				msg = bufferedReader.readLine();
				ret = Integer.valueOf(msg);
				if (ret > 2 || ret < 1) {
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
	

	public int getDays()  {
		Date dates = new Date();
		long offtime = dates.getTime()/1000;
		long ontime = startTime/1000;
		long second = offtime - ontime;
		String numberAsString = String.valueOf(second);
		int number = Integer.parseInt(numberAsString);
		int days = number/20;
		return days;
}

	
	public synchronized void write(String msg, BufferedWriter bufferedWriter) throws IOException {
		if(null != bufferedWriter) {
			bufferedWriter.write(msg+System.lineSeparator());
			bufferedWriter.flush();
		}
	}

	public void runfor84days() {
		
	     final Runnable timelyrun = new Runnable() {
	       public void run() 
	       {
	    	   
	    	   if(getDays() >= 34 && getDays() < 48) {
	    	   log.info("Scheduled task call on the day "+getDays()); 
	    	   try {
				mkobj.firstMark();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	   }
	    	   else if(getDays() >= 48 && getDays() < 68) {
	    		   log.info("Scheduled task call on the day "+getDays());
	    		   try {
					mkobj.SecondMark();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		    	   }
	    	   else if(getDays() >= 68 && getDays() < 85) {
	    		   log.info("Scheduled task call on the day "+getDays());
	    		   try {
					mkobj.ThirdMark();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		    	   }
	    	}
	     };
	     final ScheduledFuture<?> runHandle =
	       scheduler.scheduleAtFixedRate(timelyrun, 720, 480, SECONDS);
	     getDays();
	     scheduler.schedule(new Runnable() {
	       public void run() { runHandle.cancel(true); }
	     }, 90 * 20, SECONDS);
	   }
	
	
}



