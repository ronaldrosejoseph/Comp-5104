package home;
import org.apache.log4j.Logger;

import home.Home;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.SQLException;

public class Clerk {
	Clerk cobj = null;
	Home hobj  = new Home();
	private static final Logger log = Logger.getLogger(Clerk.class);
	public boolean Validation(BufferedWriter bufferedWriter, BufferedReader bufferedReader) throws IOException, SQLException {
		cobj = new Clerk();
		log.info("Inside clerk class");
		Clerk.write("Please enter the password: ", bufferedWriter);
		String msg = "";
		int pass = 0;
		int inc = 0;
		int i = 0;
		do {
			try {
				msg = bufferedReader.readLine();
				pass = Integer.valueOf(msg);
				if(pass == 1234)
					i = 0;
				else {
					inc++;
					if(inc == 3) {
						Clerk.write("Wrong password entered thrice. Redirecting..\n", bufferedWriter);
						hobj.getData(bufferedWriter, bufferedReader);
						return false;
					}
					else {
					Clerk.write("Wrong Password...\nCome again!!!\n ", bufferedWriter);
					i = 1;
					}
					}
			}catch (NumberFormatException e) {
				inc++;
				if(inc == 3) {
					Clerk.write("Wrong password entered thrice. Redirecting..\n\n", bufferedWriter);
					hobj.getData(bufferedWriter, bufferedReader);
					return false;
				}
				else	{
				Clerk.write("Wrong Password...\nCome again!!!\n ", bufferedWriter);
				i = 1;
				}
				}
							
		}while(i == 1);
		if(pass == 1234)
		{
			log.info("Clerk is authenticated");
			ClerkAction caobj = new ClerkAction();
			caobj.clientChoices(bufferedWriter,bufferedReader);
			return true;
		}
		return false;
		
		
	}
	
	public synchronized static void write(String msg, BufferedWriter bufferedWriter) throws IOException {
		if(null != bufferedWriter) {
			bufferedWriter.write(msg+System.lineSeparator());
			bufferedWriter.flush();
		}
	}

}
