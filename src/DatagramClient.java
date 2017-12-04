import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class DatagramClient extends Thread {
	private App app;
	private MulticastSocket socket;
	private InetAddress groupIp;

	private static int maxSize = 10000;

	public DatagramClient(App app) throws IOException {
		super("DatagramClient");
		this.app = app;

	}
	
	private void receiveImages(String multicastAddress, int port) {

		InetAddress ia = null;
		MulticastSocket ms = null;
		
		try {
			/* Get address */
			ia = InetAddress.getByName(multicastAddress);

			/* Setup socket and join group */
			ms = new MulticastSocket(port);
			ms.joinGroup(ia);

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
				ms.receive(dp);
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

				/* If image is complete dispay it */
				if (slicesStored == slices) {
					ByteArrayInputStream bis = new ByteArrayInputStream(
							imageData);
					BufferedImage image = ImageIO.read(bis);
					app.drawArea.setDrawArea(image);

				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ms != null) {
				try {
					/* Leave group and close socket */
					ms.leaveGroup(ia);
					ms.close();
				} catch (IOException e) {
				}
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			this.receiveImages("234.5.8.7", 8001);
		}
	}
}
