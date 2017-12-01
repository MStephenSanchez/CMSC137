
import java.awt.TextArea;
import java.io.*;
import java.net.*;
import java.util.*;

public class ServerProgram implements OnSocketListener
{
	private Server server;
	private String name;
	private App app;
	private ArrayList<InetAddress> clientList = new ArrayList<InetAddress>();
	private int currPlayerId = 0;
	private Player player;
	
	public ServerProgram(String name, App app, Player player){
		this.name = name;
		this.app = app;
		this.player = player;
	}
	
	public int updatePlayerIds(){
		return currPlayerId++;
	}
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
		String msg = "Client connected from " + hostName + ":" + port + ":" + updatePlayerIds();
		app.addChat(msg);

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

		app.addChat(msg);
		server.broadcast(msg);
	}
	
	@Override
	public void onReceived(Channel channel, String msg)
	{
		app.addChat(msg);
		server.broadcast(msg);
	}
	
	public void start() throws IOException
	{
		Scanner scanner = new Scanner(System.in);
		
		//System.out.print("Port : ");	// Get Port
		int port = 8000;//Integer.parseInt(scanner.nextLine());
		
		server = new Server(this);
		server.bind(port); // Open Server
		server.start(); // Start Accept Thread
	    new DatagramServer(app,clientList).start();
	    System.out.println("ADADDss");
		app.addServer(server);
		app.addChat("Server has started on "+server.getIPaddress().getLocalHost().getHostAddress());	
	    player.add();
	}
	
	public Server getServer(){
		return server;
	}
}

