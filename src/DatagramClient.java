import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class DatagramClient extends Thread {
	private App app;
	private MulticastSocket socket;
	private InetAddress groupIp;

	public DatagramClient(App app) throws IOException {
		super("DatagramClient");
		this.app = app;
		groupIp = InetAddress.getByName("234.5.8.7");
		socket = new MulticastSocket(8001);
		socket.joinGroup(groupIp);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				byte[] buffer = new byte[65507];
				// wait for response
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				// show request to canvas
				app.drawArea.setDrawArea(packet.getData());

			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
		try {
			socket.leaveGroup(groupIp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			socket.leaveGroup(groupIp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			socket.leaveGroup(groupIp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		socket.close();
	}
}
