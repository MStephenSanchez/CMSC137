import java.io.*;
import java.net.*;
import java.util.*;

public class Channel implements Runnable
{
	private Socket socket;
	private Scanner reader;
	private PrintWriter writer;
	private String word;
	
	private boolean running;
	
	private OnSocketListener onSocketListener;
	
	public Channel(Socket socket, OnSocketListener onSocketListener)
	{
		this.socket = socket;
		this.onSocketListener = onSocketListener;
	}
	
	public Channel(Socket socket, OnSocketListener onSocketListener, String w)
	{
		this.socket = socket;
		this.onSocketListener = onSocketListener;
		this.word = w;
	}
	
	public void start()
	{
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void stop() throws IOException
	{
		running = false;
		
		writer.close();
		reader.close();
		socket.close();
	}

	@Override
	public void run()
	{
		try
		{
			OutputStream outputStream = socket.getOutputStream();
			writer = new PrintWriter(outputStream);
			
			InputStream inputStream = socket.getInputStream();
			reader = new Scanner(inputStream);
			
			if(null != onSocketListener)
				onSocketListener.onConnected(this);
			
			running = true;
			while(running)
			{
				try
				{
					String msg = reader.nextLine();

					if(null != onSocketListener) {
						onSocketListener.onReceived(this, msg);
					}

					onSocketListener.onWord(this, this.word);
				}
				catch(NoSuchElementException e)
				{
					break;
				}
			}
			
			if(null != onSocketListener)
				onSocketListener.onDisconnected(this);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void send(String msg)
	{
		// send message
		writer.println(msg);
		writer.flush();
	}
	
	public void sendWord(String w) {
		this.send(w);
	}
	
	public Socket getSocket()
	{
		return socket;
	}
}
