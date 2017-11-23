import java.io.*;
import java.net.*;
import java.util.*;

public class ClientProgram implements OnSocketListener
{
	private Socket socket;
	private String name;
	private String ip;
	private int port;
	private App app;
	
	public ClientProgram(String name, String ip, int port, App app){
		this.ip = ip;
		this.port = port;
		this.name = name;
		this.app = app;
	}
	
	@Override
	public void onConnected(Channel channel)
	{
			app.addChat("Connected to "+ ip);
			app.getChannel().send(name + " has joined the game!");
		    try {
				new DatagramClient(app).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@Override
	public void onDisconnected(Channel channel)
	{
		app.addChat("Disconnected.");
	}
	
	@Override
	public void onReceived(Channel channel, String msg)
	{
		app.addChat(msg);
	}
	
	public InetAddress getIp(){		
		return 	socket.getInetAddress();

	}
	
	public void start() throws UnknownHostException, IOException
	{
		Scanner scanner = new Scanner(System.in);
		
		socket = new Socket(ip, port);
		
//		Receive
		Channel channel = new Channel(socket, this);
		app.addChannel(channel);
		channel.start();
	
	}
}
