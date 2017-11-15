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
		//create socket for the channel
		this.socket = socket;
		this.onSocketListener = onSocketListener;
	}
	
	public void start()
	{
		//run a thread per channel
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void stop() throws IOException
	{
		//stop channel
		running = false;
		
		//close writer, reader and socket
		writer.close();
		reader.close();
		socket.close();
	}

	@Override
	public void run()
	{
		try
		{
			//create output stream for writing output
			OutputStream outputStream = socket.getOutputStream();
			writer = new PrintWriter(outputStream);
			
			//create input stream for reading input
			InputStream inputStream = socket.getInputStream();
			reader = new Scanner(inputStream);
			
			if(null != onSocketListener)
				onSocketListener.onConnected(this);
			
			running = true;
			while(running)
			{
				try
				{
					//read input message
					String msg = reader.nextLine();

					if(null != onSocketListener)
						//show message on chat
						onSocketListener.onReceived(this, msg);
				}
				catch(NoSuchElementException e)
				{
					break;
				}
			}
			
			if(null != onSocketListener)
				//channel disconnected
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
		//get socket of channel
		return socket;
	}
}
