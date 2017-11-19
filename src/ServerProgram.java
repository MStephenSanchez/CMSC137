
import java.io.*;
import java.net.*;
import java.util.*;

public class ServerProgram implements OnSocketListener
{
	private Server server;
	private Chat chat;
	private String name;
	private SwingPaint swingpaint;
	private ArrayList<InetAddress> clientList = new ArrayList<InetAddress>();
	
	@Override
	public void onConnected(Channel channel)
	{
		Socket socket = channel.getSocket();
		String hostName = "";
		try {
			hostName = socket.getInetAddress().getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int port = socket.getPort();
		clientList.add(socket.getInetAddress());
		String msg = "Client connected from " + hostName + ":" + port;
		chat.show(msg);

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

		chat.show(msg);
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
		
		//System.out.print("Port : ");	// Get Port
		int port = 8000;//Integer.parseInt(scanner.nextLine());
		
		System.out.print("Name : ");
		String name = scanner.nextLine();
		
		server = new Server(this);
		server.bind(port); // Open Server
		server.start(); // Start Accept Thread
		swingpaint = new SwingPaint(server.getIPaddress(),false);
	    swingpaint.show();
	    new DatagramServer(swingpaint,clientList).start();
		chat = new Chat(server,name);
		chat.show("Server has started on "+server.getIPaddress().getLocalHost().getHostAddress());	
	    
	}
}

