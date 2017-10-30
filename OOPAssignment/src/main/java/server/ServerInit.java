package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import home.Home;

public class ServerInit 
{
	public static final int PORT = 4444;
	private Socket socket = null;
	public static void main(String[] args) throws IOException 
	{
		new ServerInit().runServer();

	}
	public void runServer() throws IOException
	{
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("Server is running...\nEvent has started");
		Home home = new Home();
		home.runfor84days();
		while(true)
		{
			socket = serverSocket.accept();
			new ServerThread(socket,this).start();
		}
	}
	

}
