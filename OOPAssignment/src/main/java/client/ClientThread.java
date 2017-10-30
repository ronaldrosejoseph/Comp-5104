package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class ClientThread extends Thread{
	Socket socket;
	BufferedReader bufferedReader = null;
	boolean connectd = true;
	public ClientThread(Socket socket)
	{
		this.socket = socket;
		this.start();
	}
	
	@Override
	public void run() {
		try {
			String message = null;
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("Welcome to my fist Java Project. If you find any bugs free feel to fix it...\nPress Enter to continue");
			while(connectd) {
				message = bufferedReader.readLine();
				System.out.println(message );
				}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
