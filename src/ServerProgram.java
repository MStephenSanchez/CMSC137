import java.io.*;
import java.net.*;
import java.util.*;

public class ServerProgram implements OnSocketListener
{
	private Server server;
	private Chat chat;
	
	@Override
	public void onConnected(Channel channel)
	{
		Socket socket = channel.getSocket();
		String hostName = socket.getInetAddress().getHostName();
		int port = socket.getPort();
		
		String msg = "Client connected from " + hostName + ":" + port;
		System.out.println(msg);
		
		for (Channel c : server.getChannels())
		{
			if(c != channel)
				c.send(msg);
		}
	}
	
	@Override
	public void onDisconnected(Channel channel)
	{
		server.remove(channel);
		
		Socket socket = channel.getSocket();
		String hostName = socket.getInetAddress().getHostName();
		int port = socket.getPort();
		
		String msg = "Client disconnected from " + hostName + ":" + port;
		System.out.println();
		
		server.broadcast(msg);
	}
	
	@Override
	public void onReceived(Channel channel, String msg)
	{
		System.out.println(msg);
		server.broadcast(msg);
	}
	
	public void start() throws IOException
	{
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Port : ");	// Get Port
		int port = Integer.parseInt(scanner.nextLine());
		
		server = new Server(this);
		server.bind(port); // Open Server
		server.start(); // Start Accept Thread
		
		chat = new Chat();
		
		System.out.println("Server has started on "+server.getIPaddress());

		// Send
		while (true)
		{
			String msg = scanner.nextLine();

			if (msg.isEmpty())
				break;

			server.broadcast("Server >> " + msg);
		}
		
		scanner.close();
		server.stop();

		System.out.println("Server has closed.");
	}
}
