import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements Runnable
{
	private boolean running;
	
	private ServerSocket serverSocket;
	private ArrayList<Channel> channels;
	
	private OnSocketListener onSocketListener;
	
	public int playerCount = 1;
	
	public Server(OnSocketListener onSocketListener)
	{
		this.onSocketListener = onSocketListener;
	}
	
	public InetAddress getIPaddress() {
		//get server IP
		return serverSocket.getInetAddress();
	}
	
	public void bind(int port) throws IOException
	{
		//create new server socket
		this.serverSocket = new ServerSocket(port);
	}
	
	public void start()
	{
		Thread thread = new Thread(this);
		thread.start();
	}

	public void stop() throws IOException
	{
		//stop server
		running = false;
		//close server socket
		serverSocket.close();
	}
	
	@Override
	public void run()
	{
		//create channel list
		channels = new ArrayList<>();
		
		running = true;
		while(running)
		{
			try
			{
				// accept incoming connection
				Socket socket = serverSocket.accept();
				
				// create new channel for connection
				Channel channel = new Channel(socket, onSocketListener);
				channel.start();
				playerCount = playerCount + 1;
				System.out.println(playerCount);
				// add the channel to channel list
				channels.add(channel);
			} 
			catch (SocketException e)
			{
				break;
			}
			catch (IOException e)
			{
				break;
			}
		}
		
		try
		{
			for(Channel channel : channels)
			{
				//stop all channels when server dies
				channel.stop();
			}
			//clear channel list
			channels.clear();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void broadcast(String msg)
	{
		if(!running)
			return;
		
		for(Channel channel : channels)
		{
			//send message to each channel
			channel.send(msg);
		}
	}
	
	public void remove(Channel channel)
	{
		if(!running)
			return;
		//remove a channel (disconnect)
		channels.remove(channel);
	}
	
	public ArrayList<Channel> getChannels()
	{
		//get list of channels
		return channels;
	}
}
