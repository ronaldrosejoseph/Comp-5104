package Home;

import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

import org.apache.log4j.Logger;


public class Home {
	//	class for clerk or student selection
	
		private static final Logger log = Logger.getLogger(Home.class);
		public int getData() 
		{
			log.info("---------------------------------------\nStarting program\n-------------------------------------");
			int choice;
			Scanner input = new Scanner(System.in);
			do	{
			
				System.out.print("1. Clerk \n2. Student \nEnter your choice: ");
				while(!input.hasNextInt()) {
					System.out.println("Don't be a stupid...\nCome again!!! ");
				    input.next();
				}
				choice = input.nextInt();
				log.info("User gives this option: "+choice);
				switch(choice) 
					{
					case  1:
						return 1;
						
					case 2:
						return 2;
						
					default:
						System.out.println("Come again ");
						break;
					}
				}while(choice != 1 && choice != 2);
			return 0;
		}
	}


