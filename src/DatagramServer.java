import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class DatagramServer extends Thread{
	int port = 8000;
	private App app;
	private ArrayList<InetAddress> clientList;
    protected DatagramSocket socket = null;

	public DatagramServer(App app, ArrayList<InetAddress> clientList) throws SocketException{
		super("DatagramServer");
		this.clientList = clientList;
		this.app = app;
		socket = new DatagramSocket(port);
		app.drawArea.setDatagramServer(this);
	}
	
	public void multiSendData(byte[] buffer) throws IOException{
		InetAddress groupAddress = InetAddress.getByName("234.5.8.7");
        DatagramPacket packet;
        packet = new DatagramPacket(buffer, buffer.length, groupAddress, 8001);
        socket.send(packet);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		 while (true) {
	            try {

	                byte[] buffer = new byte[65507];
	                // wait for request
	                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
	                socket.receive(packet);
	                
	                // show request to canvas
	                app.drawArea.setDrawArea(packet.getData());

	                //send request data to other clients
	                multiSendData(packet.getData());

	            } catch (IOException e) {
	                e.printStackTrace();
	                break;
	            }
	     }
		 socket.close();	

	}
}
