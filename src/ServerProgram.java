
import java.io.*;
import java.net.*;
import java.util.*;

public class ServerProgram implements OnSocketListener
{
	private Server server;
	private Chat chat;
	private String name;
	
	@Override
	public void onConnected(Channel channel)
	{
		//get socket of channel
		Socket socket = channel.getSocket();
		//get hostname
		String hostName = socket.getInetAddress().getHostName();
		//get port
		int port = socket.getPort();
		
		String msg = "Client connected from " + hostName + ":" + port;
		//show on current chat
		chat.show(msg);
		
		for (Channel c : server.getChannels())
		{
			if(c != channel)
				//send connection message to all other channels
				c.send(msg);
		}
	}
	
	@Override
	public void onDisconnected(Channel channel)
	{
		//remove channel
		server.remove(channel);
		
		//get socked
		Socket socket = channel.getSocket();
		//get hostname
		String hostName = socket.getInetAddress().getHostName();
		//get port
		int port = socket.getPort();
		
		String msg = "Client disconnected from " + hostName + ":" + port;

		//show on chat
		chat.show(msg);
		//broadcast disconnect
		server.broadcast(msg);
	}
	
	@Override
	public void onReceived(Channel channel, String msg)
	{
		chat.show(msg);
		server.broadcast(msg);
	}
	
	public void start() throws IOException
	{
		Scanner scanner = new Scanner(System.in);
		
		// Get Port
		System.out.print("Port : ");	
		int port = Integer.parseInt(scanner.nextLine());
		
		// Get # of players
		System.out.print("Number of players : ");	
		int numberOfPlayers = Integer.parseInt(scanner.nextLine());
		
		// Get player name
		System.out.print("Name : ");	
		String name = scanner.nextLine();
		
		//create new server
		server = new Server(this);	
		// Open Server
		server.bind(port);
		// Start Accept Thread
		server.start(); 
		
		//open chat ui
		chat = new Chat(server,name);
		chat.show("Server has started on "+server.getIPaddress().getLocalHost().getHostAddress());	
		
	}
}

