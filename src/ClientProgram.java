import java.io.*;
import java.net.*;
import java.util.*;

public class ClientProgram implements OnSocketListener
{
	private Socket socket;
	private String name;
	private SwingPaint swingpaint;
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
//		    this.swingpaint = new SwingPaint(getIp(),true);
//		    swingpaint.show();
//		    try {
//				new DatagramClient(swingpaint).start();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
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
