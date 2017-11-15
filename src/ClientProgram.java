import java.io.*;
import java.net.*;
import java.util.*;

public class ClientProgram implements OnSocketListener
{
	private Server server;
	private Chat chat;
	private String name;
	private String ip;
	
	@Override
	public void onConnected(Channel channel)
	{
		if(chat!=null){
			//connected to server
			chat.show("Connected to "+ ip);
		}
	}
	
	@Override
	public void onDisconnected(Channel channel)
	{
		//disconnect from server
		chat.show("Disconnected.");
	}
	
	@Override
	public void onReceived(Channel channel, String msg)
	{
		//show chat message
		chat.show(msg);
	}
	
	public void start() throws UnknownHostException, IOException
	{
		Scanner scanner = new Scanner(System.in);
		
		//get player name
		System.out.print("Name : ");
		String name = scanner.nextLine();
		
		//get IP to connect to
		System.out.print("IP : ");
		ip = scanner.nextLine();
		
		//get port number to connect to
		System.out.print("Port : ");
		int port = Integer.parseInt(scanner.nextLine());
		
		//create new socket for the client
		Socket socket = new Socket(ip, port);
		
		//create a new channel to receive messages
		Channel channel = new Channel(socket, this);
		
		//open chat ui
		chat = new Chat(channel,name);

		//start the channel
		channel.start();
		
		
		
//		// Send
//		while(true)
//		{
//			String msg = scanner.nextLine();
//			
//			if(msg.isEmpty())
//				break;
//			
//			channel.send(name + " >> " + msg);
//		}
//		
//		scanner.close();
//		channel.stop();
//		
//		System.out.println("Closed");
	}
}
