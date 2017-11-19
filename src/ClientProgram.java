import java.io.*;
import java.net.*;
import java.util.*;

public class ClientProgram implements OnSocketListener
{
	private Socket socket;
	private Chat chat;
	private String name;
	private SwingPaint swingpaint;
	private String ip;
	
	@Override
	public void onConnected(Channel channel)
	{
		if(chat!=null){
			chat.show("Connected to "+ ip);
		    this.swingpaint = new SwingPaint(getIp(),true);
		    swingpaint.show();
		    try {
				new DatagramClient(swingpaint).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void onDisconnected(Channel channel)
	{
		chat.show("Disconnected.");
	}
	
	@Override
	public void onReceived(Channel channel, String msg)
	{
		chat.show(msg);
	}
	
	public InetAddress getIp(){		
		return 	socket.getInetAddress();

	}
	
	public void start() throws UnknownHostException, IOException
	{
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Name : ");
		String name = scanner.nextLine();
		
		System.out.print("IP : ");
		ip = scanner.nextLine();
		
		System.out.print("Port : ");
		int port = Integer.parseInt(scanner.nextLine());
		
		socket = new Socket(ip, port);
		
//		Receive
		Channel channel = new Channel(socket, this);
		chat = new Chat(channel,name);
		
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
