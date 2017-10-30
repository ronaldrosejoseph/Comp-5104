package home;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


@RunWith(PowerMockRunner.class)
@PrepareForTest({BufferedReader.class, BufferedWriter.class, Clerk.class})
public class clerkTest {
	
	@Mock
	BufferedReader reader;
	
	@Mock
	BufferedWriter writer;

	@Spy
	Clerk clerk = new Clerk();
	
	@Mock
	ClerkAction clerkact;
	

	@Before
	public void setUp() throws Exception {
		PowerMockito.whenNew(ClerkAction.class).withNoArguments().thenReturn(clerkact);
		PowerMockito.when(clerkact.clientChoices((BufferedWriter) Mockito.any(), (BufferedReader) Mockito.any())).thenReturn(true);
	}
	
	
	@Test
	public void testClerkAuthentication() {
		System.out.println("Testing clerk authentication \n");
		boolean noErrors = true;
		try {
			PowerMockito.when(reader.readLine()).thenReturn("1234");
			noErrors = clerk.Validation(writer, reader);
		} catch (IOException e) {
			noErrors = false;
		} catch (SQLException e) {
			noErrors = false;
		}
		assertTrue(noErrors);
	}



}


