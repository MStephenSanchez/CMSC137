import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class DatagramServer extends Thread{
	int port = 8000;
	int session = 0;
	private App app;
	private ArrayList<InetAddress> clientList;
    protected DatagramSocket socket = null;
    String ip;
	private static int maxSize = 10000;
	

    
	public DatagramServer(App app, ArrayList<InetAddress> clientList, String ip) throws SocketException{
		super("DatagramServer");
		this.clientList = clientList;
		this.ip = ip;
		this.app = app;
		this.socket = new DatagramSocket(port);
		app.drawArea.setDatagramServer(this);
	}
	
	private void receiveImages(String multicastAddress, int port) {

		InetAddress ia = null;
		
		try {
			/* Get address */
			ia = InetAddress.getByName(multicastAddress);

			int currentSession = -1;
			int slicesStored = 0;
			int[] slicesCol = null;
			byte[] imageData = null;
			boolean sessionAvailable = false;

			/* Setup byte array to store data received */
			byte[] buffer = new byte[maxSize];

			/* Receiving loop */
			while (true) {
				/* Receive a UDP packet */
				DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
				this.socket.receive(dp);
				byte[] data = dp.getData();

				/* Read header infomation */
				short session = (short) (data[1] & 0xff);
				short slices = (short) (data[2] & 0xff);
				int maxPacketSize = (int) ((data[3] & 0xff) << 8 | (data[4] & 0xff)); // mask
				// the
				// sign
				// bit
				short slice = (short) (data[5] & 0xff);
				int size = (int) ((data[6] & 0xff) << 8 | (data[7] & 0xff)); // mask
				// the
				// sign
				// bit

				/* If 128 falg is set, setup start values */
				if ((data[0] & 128) == 128) {
					if (session != currentSession) {
						currentSession = session;
						slicesStored = 0;
						/* Consturct a appropreately sized byte array */
						imageData = new byte[slices * maxPacketSize];
						slicesCol = new int[slices];
						sessionAvailable = true;
					}
				}

				/* If package belogs to current session */
				if (sessionAvailable && session == currentSession) {
					if (slicesCol != null && slicesCol[slice] == 0) {
						slicesCol[slice] = 1;
						System.arraycopy(data, 8, imageData, slice
								* maxPacketSize, size);
						slicesStored++;
					}
				}

				/* If image is complete display it */
				if (slicesStored == slices) {
					ByteArrayInputStream bis = new ByteArrayInputStream(
							imageData);
					BufferedImage image = ImageIO.read(bis);
					app.drawArea.setDrawArea(image);
					 //send request data to other clients
					ByteArrayOutputStream output = new ByteArrayOutputStream();      
					BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),BufferedImage.TYPE_3BYTE_BGR);
					Graphics g = bufferedImage.getGraphics();
					g.drawImage(image, 0, 0, null);
					try {
						ImageIO.write(bufferedImage, "jpg", output);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            multiSendData(output.toByteArray());

					  
					  
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	
	private boolean sendImage(byte[] imageData, String multicastAddress,int port) {
		InetAddress ia;

		boolean ret = false;
		int ttl = 2;

		try {
			ia = InetAddress.getByName(multicastAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return ret;
		}

		MulticastSocket socket = null;

		try {
			socket = new MulticastSocket();
			socket.setTimeToLive(ttl);
			DatagramPacket dp = new DatagramPacket(imageData, imageData.length,
					ia, port);
			socket.send(dp);
			ret = true;
		} catch (IOException e) {
			e.printStackTrace();
			ret = false;
		} finally {
			if (socket != null) {
					socket.close();
			}
		}

		return ret;
	}
	
	public void multiSendData(byte[] buffer) throws IOException{
		int packets = (int) Math.ceil(buffer.length / (float)(10000 - 8));

		/* Loop through slices */
		for(int i = 0; i <= packets; i++) {
			int flags = 0;
			flags = i == 0 ? flags | 128: flags;
			flags = (i + 1) * (10000 - 8) > buffer.length ? flags | 64 : flags;

			int size = (flags & 64) != 64 ? (10000 - 8) : buffer.length - i * (10000 - 8);

			/* Set additional header */
			byte[] data = new byte[8 + size];
			data[0] = (byte)flags;
			data[1] = (byte)session;
			data[2] = (byte)packets;
			data[3] = (byte)((10000 - 8) >> 8);
			data[4] = (byte)(10000 - 8);
			data[5] = (byte)i;
			data[6] = (byte)(size >> 8);
			data[7] = (byte)size;

			/* Copy current slice to byte array */
			System.arraycopy(buffer, i * (10000 - 8), data, 8, size);
			/* Send multicast packet */

			this.sendImage(data, "234.5.8.7", 8001);

			/* Leave loop if last slice has been sent */
			if((flags & 64) == 64) break;
		}
		
		/* Increase session number */
		session = session < 255 ? ++session : 0;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		 while (true) {
	         this.receiveImages(ip, 8000);       
	     }
	}
}
