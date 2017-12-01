import java.io.*;
import java.net.*;
import java.util.*;

public class Channel implements Runnable
{
	private Socket socket;
	private Scanner reader;
	private PrintWriter writer;
	
	private boolean running;
	
	private OnSocketListener onSocketListener;
	
	public Channel(Socket socket, OnSocketListener onSocketListener)
	{
		this.socket = socket;
		this.onSocketListener = onSocketListener;
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
			this.writer = new PrintWriter(outputStream);
			
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

					if(null != onSocketListener)
						onSocketListener.onReceived(this, msg);
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
	
	public Socket getSocket()
	{
		return socket;
	}
	
	public PrintWriter getWriter(){
		return writer;
	}
}
