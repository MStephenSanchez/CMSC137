/*
 * UI implementation of Drawing drawing
 * AUTHORS:
 * 	Gonzales, Ma. Pauline
 * 	Sanchez, Mark Stephen
 * 	Sta. Ana, Yves Robert
 * 	Vaethbrueckner, Jan-Josel
 * References: https://www.ntu.edu.sg/home/ehchua/programming/java/J4a_GUI.html
 * 			   https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class App {
	JFrame mainFrame;
	JButton clearBtn, blackBtn, blueBtn, greenBtn, redBtn;
	JLabel enterWord;
	JTextField word, enterChat;
	TextArea chatArea;
	DrawArea drawArea;
	static String playerName;
	static String portNum;
	static String ipAddr;
	ActionListener actionListener = new ActionListener() {
	 
	  public void actionPerformed(ActionEvent e) {
	      if (e.getSource() == clearBtn) {
	        drawArea.clear();
	      } else if (e.getSource() == blackBtn) {
	        drawArea.black();
	      } else if (e.getSource() == blueBtn) {
	        drawArea.blue();
	      } else if (e.getSource() == greenBtn) {
	        drawArea.green();
	      } else if (e.getSource() == redBtn) {
	        drawArea.red();
	      } else if(e.getSource() == enterChat) {
	    	  chatArea.append(playerName + ": " + enterChat.getText() + "\n");
	    	  enterChat.setText("Type your guess here...");
	    	  enterChat.selectAll();
	      }
	    }
	  };
	public App(String name, String port, String ip) {
		playerName = name;
		portNum = port;
		ipAddr = ip;
	}

	public static void main(String[] args) {
		    new App(playerName, portNum, ipAddr).show();
		  }

	public void show(){
		//main frame
		mainFrame = new JFrame("Drawing Drawing!");
		Container content = mainFrame.getContentPane();
		
		content.setLayout(new BorderLayout());
		drawArea = new DrawArea();
		
		//panel for the canvas and colors
		JPanel canvas = new JPanel();
		canvas.setLayout(new BorderLayout());
		
		canvas.add(drawArea, BorderLayout.CENTER);
		 
	    // create controls to apply colors and call clear feature
	    JPanel controls = new JPanel();
	  
	    //Insert Commands
	    clearBtn = new JButton("Clear");
	    clearBtn.addActionListener(actionListener);
	    blackBtn = new JButton("Black");
	    blackBtn.addActionListener(actionListener);
	    blueBtn = new JButton("Blue");
	    blueBtn.addActionListener(actionListener);
	    greenBtn = new JButton("Green");
	    greenBtn.addActionListener(actionListener);
	    redBtn = new JButton("Red");
	    redBtn.addActionListener(actionListener);
	 
	    // add to controls panel
	    controls.add(greenBtn);
	    controls.add(blueBtn);
	    controls.add(blackBtn);
	    controls.add(redBtn);
	    controls.add(clearBtn);
	 
	    // add to canvas panel
	    canvas.add(controls, BorderLayout.SOUTH);
	    
	    //Enter word to guess panel
	    JPanel wordPanel = new JPanel();
	    enterWord = new JLabel("Enter Word to Draw:", JLabel.CENTER);
	    word = new JTextField(20);
	    
	    wordPanel.add(enterWord);
	    wordPanel.add(word);
	    
	    //Chat panel
	    JPanel chat = new JPanel();
	    enterChat = new JTextField("Type your guess here...");
	    enterChat.addActionListener(actionListener);
	    
	    chatArea = new TextArea(29,25);
	    chatArea.setEditable(false);
	    chat.setLayout(new BoxLayout(chat, BoxLayout.PAGE_AXIS));
	    
	    chat.add(chatArea);
	    chat.add(enterChat);
	    
	    //Players panel
	    //Static players (implemented with buttons); sample
	    JPanel players = new JPanel();
	    players.setLayout(new BoxLayout(players, BoxLayout.PAGE_AXIS));
	    JButton p1, p2, p3, p4;
	    p1 = new JButton("Player1  Points:120");
	    p2 = new JButton("Player2  Points:100");
	    p3 = new JButton("Player3  Points:210");
	    p4 = new JButton("Player4  Points:150");
	    
	    players.add(p1);
	    players.add(p2);
	    players.add(p3);
	    players.add(p4);
	    
	    //add other panels to content pane
	    content.add(canvas, BorderLayout.CENTER);
	    content.add(wordPanel, BorderLayout.SOUTH);
	    content.add(chat, BorderLayout.EAST);
	    content.add(players, BorderLayout.WEST);
	    
	    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		mainFrame.setSize(700,550);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
	}
}


