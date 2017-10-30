package client;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {

	private BufferedReader console;
	private BufferedReader streamIn;
	private BufferedWriter streamOut;
	private boolean connected = true;
	private Thread thread = null;

	public static void main(String[] args) throws UnknownHostException, IOException {
		try {
			new Client().start();
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void start() throws UnknownHostException, IOException {
		Socket socket = new Socket("localhost", 4444);;
		console	= new BufferedReader(new InputStreamReader(System.in));
		streamIn	= new BufferedReader(new InputStreamReader(socket.getInputStream()));
		streamOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		new ClientThread(socket);
		thread = new Thread(this);
		thread.start();
	}

//	@Override
	public void run() {
		while(connected) {
			try {
				String msg = console.readLine();
				streamOut.flush();
				streamOut.write(msg + System.lineSeparator());
				streamOut.flush();
				
//				System.out.println("from console");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}



}
