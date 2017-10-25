package Home;

import static org.junit.Assert.*;

import org.junit.Test;

public class clerkTest {

	@Test
	public void testClerkAuthentication() {
		System.out.println("Testing clerk authentication \n");
		clerk obj = new clerk();
		assertTrue(obj.Validation());
		}

}


