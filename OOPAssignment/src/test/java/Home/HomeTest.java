package Home;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;

public class HomeTest {
	Home obj = new Home();
	
	@Test
	public void testtimer() throws InterruptedException {
		Thread.sleep(20000);	 
		assertEquals(1,obj.getDays()); // 1 day is assumed as 20 seconds
	}
	
	@Test
	public void failtesttimer() throws InterruptedException {
		Thread.sleep(19400);	 	//500 millisecond error 
		assertEquals(2,obj.getDays()); // 2 day is assumed as 40 seconds we check for 39.4sec
	}
	
	@Test
	public void testClerkSelect() {
		System.out.println("Testing clerk selection \n");
		assertEquals(1,obj.getData());
	}
	

	@Test
	public void testStudentSelect() {
		System.out.println("\nTesting student selection \n");
		assertEquals(2,obj.getData());
	}
}
	
	

