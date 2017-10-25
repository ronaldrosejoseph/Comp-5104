package Home;
import Home.Home;
import org.apache.log4j.Logger;
import java.util.Scanner;

public class clerk {
	private static final Logger log = Logger.getLogger(clerk.class);
	public boolean Validation() {
		log.info("Inside clerk class");
		System.out.print("Please enter the password: ");
		Scanner input = new Scanner(System.in);	
		while(!input.hasNextInt()) {
			System.out.println("Come again ");
			input.next();
		}
		int pass = input.nextInt();
		Scanner newch = new Scanner(System.in);	
		if(pass != 1234)
		{
			System.out.println("Wrong Password");
			System.out.println("1: Reenter password \n2: Previous menu");
			while(!newch.hasNextInt()) 
			{
				System.out.println("Wrong Password\nCome again ");
			    newch.next();
			}
			int choice = newch.nextInt();
			log.info("User gives this option: "+choice);
			switch(choice) 
			{
			case  1:
				Validation();
				return true;
			case 2:
				Home obj = new Home();
				obj.getData();
				break;
			default:
				System.out.println("Stop messing with me");
				Validation();
				return true;
				}
		}
		else {
			log.info("Clerk is authenticated");
			return true;
		}
		return false;
		
	
		
	}

}
