package Home;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;

public class HomeTest {

	@Test
	public void testClerkSelect() {
		Home obj = new Home();
		assertEquals(1,obj.getData());
	}
	@Test
	public void testStudentSelect() {
		Home obj = new Home();
		assertEquals(2,obj.getData());
	}

}
