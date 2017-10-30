package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import home.Home;

public class ServerThread extends Thread 
{
	Socket socket;
	ServerInit serverInit;
	BufferedWriter bufferedWriter;
	BufferedReader bufferedReader;
	boolean connected = true; 
	Home home = new Home();
	ServerThread(Socket socket, ServerInit serverInit)
	{
		this.socket = socket;
		this.serverInit = serverInit;
	}
	public void run() {
		try {
			String message = null;
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			while(connected) {
				message = bufferedReader.readLine();
				home.getData(bufferedWriter, bufferedReader);
				}
			socket.close();
		}
		catch(Exception e ) {
			e.printStackTrace();
		}
		
		
	}

}
