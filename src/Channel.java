import java.io.*;
import java.net.*;
import java.util.*;

public class Channel implements Runnable
{
	private Socket socket;
	private Scanner reader;
	private PrintWriter writer;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	private Word currWord;
	private String tempString;
	
	
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
			
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			//this.writer = new PrintWriter(outputStream);
			System.out.println("Started");
			System.out.println(objectOutputStream);
			
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			//reader = new Scanner(inputStream);
			System.out.println(objectInputStream);
			
			if(null != onSocketListener)
				onSocketListener.onConnected(this);
			
			Object object = null;
			
			String msg;
			running = true;
			while(running)
			{
				try {
					
					object =(String) objectInputStream.readObject();
					msg = (String) object;
					onSocketListener.onReceived(this, msg);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
	
	public void sendString(String msg)
	{
		// send message
		try {
			objectOutputStream.writeObject(msg);
			objectOutputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void sendObject(Word word) throws IOException
	{
		// send message
		System.out.println("AD");
		System.out.println(objectOutputStream);
		objectOutputStream.writeObject(word);
		objectOutputStream.flush();
	}

	public Socket getSocket()
	{
		return socket;
	}
	
	private void readObject( ObjectInputStream objectInputStream ) throws IOException, ClassNotFoundException{
		objectInputStream.defaultReadObject();
		
	}
	
	public PrintWriter getWriter(){
		return writer;
	}
	
	public ObjectOutputStream retOutputStream(){
		return objectOutputStream;
	}
}
