import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements Runnable
{
	private boolean running;
	private WordList wordList;
	
	private ServerSocket serverSocket;
	private ArrayList<Channel> channels;
	private ArrayList<Channel> objChannels;
	private OnSocketListener onSocketListener;
	
	public Server(OnSocketListener onSocketListener)
	{
		this.onSocketListener = onSocketListener;
	}
	
	public InetAddress getIPaddress() {
		return serverSocket.getInetAddress();
	}
	
	public void bind(int port) throws IOException
	{
		this.serverSocket = new ServerSocket(port);
	}
	
	public void start()
	{
		Thread thread = new Thread(this);
		thread.start();
	}

	public void stop() throws IOException
	{
		running = false;
		serverSocket.close();
	}
	
	public void broadcast(String msg)
	{
		if(!running)
			return;
		
		for(Channel channel : channels)
		{
			channel.send(msg);
		}
	}
	
	@Override
	public void run()
	{
		channels = new ArrayList<>();
		objChannels = new ArrayList<>();
		wordList = new WordList();
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
				
				Word word = new Word(wordList.getRandomWord(),20);
				
				
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
				channel.stop();
			}
			
			channels.clear();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	
	public void remove(Channel channel)
	{
		if(!running)
			return;
		
		channels.remove(channel);
	}
	
	public ArrayList<Channel> getChannels()
	{
		return channels;
	}
}
