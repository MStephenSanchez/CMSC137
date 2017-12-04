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
	private Player player;
	
	public ClientProgram(String name, String ip, int port, App app, Player player){
		this.ip = ip;
		this.port = port;
		this.name = name;
		this.app = app;
		this.player = player;
	}
	
	@Override
	public void onConnected(Channel channel)
	{
			app.addChat("Connected to "+ ip);
			//app.getChannel().sendString(name + " has joined the game!");
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
	
	@Override
	public void onReceivedObject(Channel channel, Word word)
	{
		//DO SOMETHING
		System.out.println(word.getWord());
	}
	
	public InetAddress getIp(){		
		return 	socket.getInetAddress();

	}
	
	public void start() throws UnknownHostException, IOException
	{
		socket = new Socket(ip, port);
		
//		Receive
		Channel channel = new Channel(socket, this);
		app.addChannel(channel);
		channel.start();
		player.add();
	
	}
}
