/*
 *Login window
 * AUTHORS:
 * 	Gonzales, Ma. Pauline
 * 	Sanchez, Mark Stephen
 * 	Sta. Ana, Yves Robert
 * 	Vaethbrueckner, Jan-Josel
 * References: https://www.ntu.edu.sg/home/ehchua/programming/java/J4a_GUI.html
 * Notes:
 * 	Need to check if entered IP Address is valid or not
 * 	Convert IP Address and Port number to Integers
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Login {
	
	private JFrame frame;
	private JLabel nameLabel, welcome, portLabel, ipLabel;
	private JButton play;
	TextField name, port, ip;
	private JPanel panel = new JPanel();
	
	//After clicking the Play button, the main game window will open (App class)
	ActionListener actionListener = new ActionListener() {
		 
		  public void actionPerformed(ActionEvent e) {
		      if (e.getSource() == play) {
		    	  //will ask to enter a name and/or IP address before entering the game
		    	  if(name.getText().isEmpty() && ip.getText().isEmpty()) {
		    		  JOptionPane.showMessageDialog(frame, "Enter your Name and IP Address");
		    	  } else if(name.getText().isEmpty()) {
		    		  JOptionPane.showMessageDialog(frame, "Enter your Name");
		    	  } else if(ip.getText().isEmpty()) {
		    		  JOptionPane.showMessageDialog(frame, "Enter IP Address");
		    	  }
		    	  else {
		    		  frame.dispose();
		    		  new App(name.getText(), port.getText(), ip.getText()).show();
		    	  }
		    	
		      }
		  }
	};
	
	public static void main(String[] args) {
		    new Login().show();
	}
	
	//game window implementation
	public void show() {
		frame = new JFrame("Drawing Drawing!");
	
		//panel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		
		nameLabel = new JLabel("Enter your Name:", JLabel.CENTER);
		welcome = new JLabel("Welcome to DRAWING DRAWING!", JLabel.CENTER);
		portLabel = new JLabel("Enter port number:", JLabel.CENTER);
		ipLabel = new JLabel("Enter IP Address:", JLabel.CENTER);
		
		play = new JButton("Play!");
		play.addActionListener(actionListener);
		
		name = new TextField("", 10);
		port = new TextField("8000", 10);
		ip = new TextField("", 15);
		
		/*
		panel.add(welcome);
		panel.add(nameLabel);
		panel.add(name);
		panel.add(portLabel);
		panel.add(port);
		panel.add(play);
		*/
		
		JPanel namePanel = new JPanel();
		namePanel.add(nameLabel);
		namePanel.add(name);
		
		JPanel portPanel = new JPanel();
		portPanel.add(portLabel);
		portPanel.add(port);
		
		JPanel ipPanel = new JPanel();
		ipPanel.add(ipLabel);
		ipPanel.add(ip);
		
		panel.add(welcome);
		panel.add(namePanel);
		panel.add(ipPanel);
		panel.add(portPanel);
		panel.add(play);
		
		content.add(panel, BorderLayout.CENTER);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.setSize(350,220);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}