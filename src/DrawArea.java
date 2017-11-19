import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
 
/* REFERENCE : http://www.ssaurel.com/blog/learn-how-to-make-a-swing-painting-and-drawing-application/ */
public class DrawArea extends JComponent {
 
  // Image in which we're going to draw
  private BufferedImage image;
  // Graphics2D object ==> used to draw on
  private Graphics2D g2;
  // Mouse coordinates
  private int currentX, currentY, oldX, oldY;
  private InetAddress ip;
  int port = 8000;
  private Boolean isClient = false;
  DatagramServer ds=null;
 
  public DrawArea(InetAddress ip, Boolean isClient) {
	this.ip=ip;
	this.isClient = isClient;
    setDoubleBuffered(false);
    addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        // save coord x,y when mouse is pressed
        oldX = e.getX();
        oldY = e.getY();
      }
    });
 
    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        // coord x,y when drag mouse
        currentX = e.getX();
        currentY = e.getY();
 
        if (g2 != null) {
          // draw line if g2 context not null
          g2.drawLine(oldX, oldY, currentX, currentY);
          // refresh draw area to repaint
          repaint();
          // store current coords x,y as olds x,y
          oldX = currentX;
          oldY = currentY;
        }
      }
    });
  }
  
  protected void paintComponent(Graphics g) {
    //Draw the canvas
    int width = getSize().width;
    int height = getSize().height;
    if (image == null) {
      image = (BufferedImage) createImage(width, height);
      g2 = (Graphics2D) image.getGraphics();
      // enable antialiasing
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      // clear draw area (set background to white)
      clear();
    }
 
    g.drawImage(image, 0, 0, null);
    try { 
        SendData();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
 
  // now we create exposed methods
  public void clear() {
    g2.setPaint(Color.white);
    // draw white on entire draw area to clear
    g2.fillRect(0, 0, getSize().width, getSize().height);
    g2.setPaint(Color.black);
    repaint();
  }
 
  //Insert functions here 
  
  public void SendData() throws IOException{
	  byte[] buffer = getByteImage().toByteArray();
	  if(isClient){
	      DatagramSocket datagramSocket = new DatagramSocket();
	      DatagramPacket packet = new DatagramPacket(buffer, buffer.length, ip, port);
	      packet.setData(buffer);
	      datagramSocket.send(packet);
	      datagramSocket.close();
	  }else{
		  if(ds!=null){
		      ds.multiSendData(buffer);
		  }
	  }
  }
  
  public void setDrawArea(byte[] img) throws IOException{
	  BufferedImage bimg = (ImageIO.read(new ByteArrayInputStream(img)));
	  g2.drawImage(bimg, null, 0, 0);
	  repaint();
  }
  
  //returns converted byte stream from image 
  private ByteArrayOutputStream getByteImage(){
	  ByteArrayOutputStream output = new ByteArrayOutputStream();      
	  BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),BufferedImage.TYPE_INT_ARGB);
	  Graphics g = bufferedImage.getGraphics();
	  g.drawImage(image, 0, 0, null);
	  try {
		ImageIO.write(bufferedImage, "jpg", output);
	  } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
	  return output;
  }
  
  public void red() {
    // apply red color on g2 context
    g2.setPaint(Color.red);
  }
 
  public void black() {
    g2.setPaint(Color.black);
  }
 
  public void magenta() {
    g2.setPaint(Color.magenta);
  }
 
  public void green() {
    g2.setPaint(Color.green);
  }
 
  public void blue() {
    g2.setPaint(Color.blue);
  }

}