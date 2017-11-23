import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
public class App {
	
	private JFrame frame;
	private JButton newGame;
	private JButton join;
	private JPanel menu;
	private Boolean isServer;
	Server server=null;
	Channel channel=null;
	
	
	JFrame mainFrame;
	JButton clearBtn, blackBtn, blueBtn, greenBtn, redBtn,eraserBtn;
	JSlider strokeSize;
	JLabel enterWord;	
	JTextField word, enterChat;
	TextArea chatArea;
	DrawArea drawArea;
	static String playerName;
	static String portNum;
	static String ipAddr;
	
	 //set values for slider
    final int SLIDE_MIN = 5;
    final int SLIDE_MAX = 50;
    final int SLIDE_INIT = 5;
    
	ChangeListener changeListener = new ChangeListener(){
		  public void stateChanged(ChangeEvent e){
			  if (e.getSource() == strokeSize){
		    	  drawArea.setStroke(strokeSize.getValue());
		      }
		  }
	  };
	  
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
	      } else if(e.getSource() == eraserBtn) {
	    	drawArea.eraser();  
	      } else if(e.getSource() == enterChat) {
//	    	  chatArea.append(playerName + ": " + enterChat.getText() + "\n");
	    	  if(server!=null){
					server.broadcast(playerName +": " + enterChat.getText());
					addChat(playerName +": " + enterChat.getText());
				}else{
					System.out.println(channel);
					channel.send(playerName + ": " + enterChat.getText());
				}
				enterChat.setText("");
//	    	  enterChat.setText("Type your guess here...");
	    	  enterChat.selectAll();
	      }
	    }
	  };
	  
	public App(String name, String port, String ip, Boolean isServer) throws IOException{
		String option;
		this.isServer = isServer;
		playerName = name;
		portNum = port;
		ipAddr = ip;
	}

	public void show() throws UnknownHostException{
		
		//main frame
		mainFrame = new JFrame("Drawing Drawing!");
		Container content = mainFrame.getContentPane();
		
		content.setLayout(new BorderLayout());
		drawArea = new DrawArea(InetAddress.getByName(ipAddr), !isServer);
		
		//panel for the canvas and colors
		JPanel canvas = new JPanel();
		canvas.setLayout(new BorderLayout());
		
		canvas.add(drawArea, BorderLayout.CENTER);
		 
	    // create controls to apply colors and call clear feature
	    JPanel controls = new JPanel();
	  
	    //Insert Commands
	    strokeSize = new JSlider(JSlider.HORIZONTAL, SLIDE_MIN, SLIDE_MAX, SLIDE_INIT);
	    strokeSize.setPreferredSize(new Dimension(100, 100));
	    strokeSize.addChangeListener(changeListener);
	    clearBtn = new JButton("Clear");
	    clearBtn.addActionListener(actionListener);
	    blackBtn = new JButton("Black");
	    blackBtn.setText("");
	    blackBtn.setBackground(Color.black);
	    blackBtn.addActionListener(actionListener);
	    blueBtn = new JButton("Blue");
	    blueBtn.setText("");
	    blueBtn.setBackground(Color.blue);
	    blueBtn.addActionListener(actionListener);
	    greenBtn = new JButton("Green");
	    greenBtn.setText("");
	    greenBtn.setBackground(Color.green);
	    greenBtn.addActionListener(actionListener);
	    redBtn = new JButton("Red");
	    redBtn.setText("");
	    redBtn.setBackground(Color.red);
	    redBtn.addActionListener(actionListener);
	    eraserBtn = new JButton("Eraser");
	    eraserBtn.addActionListener(actionListener);
	 
	    // add to controls panel
	    controls.add(clearBtn);
	    controls.add(redBtn);
	    controls.add(greenBtn); 
	    controls.add(blueBtn);
	    controls.add(blackBtn);
	    controls.add(strokeSize);
	    controls.add(eraserBtn);
	    
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
	    enterChat = new JTextField("");
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
		mainFrame.setSize(900,550);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		
		if(isServer){
			ServerProgram serverProgram = new ServerProgram(playerName, this);
			try {
				serverProgram.start();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else{
			ClientProgram clientProgram = new ClientProgram(playerName, ipAddr, Integer.parseInt(portNum), this);
			try {
				
				clientProgram.start();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	// Start Client
		}
		
	}
	
	public void addChat(String msg){
		chatArea.append(msg+"\n");
	}
	
	public void addServer(Server server){
		this.server = server;
	}
	
	public void addChannel(Channel client){
		this.channel = client;
	
	}
	
	public Channel getChannel(){
		return this.channel;
	}
}
